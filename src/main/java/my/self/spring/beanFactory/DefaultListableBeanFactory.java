package my.self.spring.beanFactory;

import my.self.spring.annotation.ComponentScan;
import my.self.spring.annotation.Scope;
import my.self.spring.annotation.Service;
import my.self.spring.beanDefinition.AnnotateBeanDefinition;
import my.self.spring.beanDefinition.AnnotateGenericBeanDefinition;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, AnnotateBeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<String, AnnotateBeanDefinition>(256);

    private List<String> beanDefinitionNames = new ArrayList<String>(256);

    private Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>();

    @Override
    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {
        //源码里有一些逻辑
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }


    public void doScan() {

        for (String beanName : beanDefinitionMap.keySet()) {
            AnnotateGenericBeanDefinition bd = (AnnotateGenericBeanDefinition) beanDefinitionMap.get(beanName);
            if (bd.getClazz().isAnnotationPresent(ComponentScan.class)) {
                ComponentScan componentScan = (ComponentScan) bd.getClazz().getAnnotation(ComponentScan.class);
                //my.self.test.bean
                String basePackage = componentScan.value();
                URL resource = this.getClass().getClassLoader()
                        .getResource(basePackage.replace(".", "/"));
                System.out.println(resource);
                File file = new File(resource.getFile());
                if (file.isDirectory()) {
                    for (File f : file.listFiles()) {
                        try {
                            Class clazz = this.getClass().getClassLoader()
                                    .loadClass(basePackage.concat(".").concat(f.getName().split("\\.")[0]));
                            if (clazz.isAnnotationPresent(Service.class)) {
                                String name = ((Service) clazz.getAnnotation(Service.class)).value();
                                AnnotateGenericBeanDefinition abd = new AnnotateGenericBeanDefinition();
                                abd.setClazz(clazz);
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    abd.setScope(((Scope) clazz.getAnnotation(Scope.class)).value());
                                } else {
                                    abd.setScope("singleton");
                                }
                                beanDefinitionMap.put(name, abd);
                                // 需要有一个地方，真正的记录定义的bean
                                beanDefinitionNames.add(name);
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
        System.out.println("00");
    }


    public void preInstantiateSingleton() {
        // 初始化定义的 bean ，需要找到所有的 自定义的 beanName
        // 为什么不直接使用 beanDefinitionNames
        // beanDefinitionNames 处于并发环境，还有一个 add的逻辑
        // 如果直接使用循环，那么循环过程中一旦出现其他的线程访问了add，就会导致for循环失败(modCount)
        List<String> beanNames = new ArrayList<String>(beanDefinitionNames);
        for (String beanName : beanNames) {
            AnnotateGenericBeanDefinition bd = (AnnotateGenericBeanDefinition) beanDefinitionMap.get(beanName);
            if (bd.getScope().equals("singleton")) {
                // 创建单例对象，然后把单例对象保存到单例池（内存 or 缓存）里面.
                // getBean 方法里面包含了创建对象，然后放到 singletonObjects 里
                getBean(beanName);
            }
        }

    }

    //只有bean都注册上以后，才能有 getBean
    @Override
    public Object getBean(String beanName) {
        return doGetBean(beanName);
    }

    private Object doGetBean(String beanName) {
        Object bean = singletonObjects.get(beanName);
        if (bean != null) {
            return bean;
        }
        //需要根据 beanDefinition 创建bean
        AnnotateGenericBeanDefinition bd = (AnnotateGenericBeanDefinition) beanDefinitionMap.get(beanName);
        Object cBean = createBean(beanName, bd);
        if (bd.getScope().equals("singleton")) {
            // createBean 方法其实是完成了beanDefinition 转真正实体对象的地方
            singletonObjects.put(beanName, cBean);
        }
        return cBean;
    }

    private Object createBean(String beanName, AnnotateGenericBeanDefinition bd) {
        try {
            return bd.getClazz().getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }
}
