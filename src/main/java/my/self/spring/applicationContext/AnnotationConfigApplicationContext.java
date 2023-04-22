package my.self.spring.applicationContext;

import my.self.spring.beanDefinition.AnnotateBeanDefinition;
import my.self.spring.beanDefinition.AnnotationBeanDefinitionReader;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;

public class AnnotationConfigApplicationContext
        implements BeanDefinitionRegistry {

    private AnnotationBeanDefinitionReader reader;


    //如果有人调用无参构造，必须先调用父类的无参构造
    public AnnotationConfigApplicationContext() {
        this.reader = new AnnotationBeanDefinitionReader(this);
    }

    public AnnotationConfigApplicationContext(Class<?> componentClass) {
        // 1.读 componentClass 也就是我们的扫描路径 所在的类 AppConfig.定义一个阅读器，专门读取 AnnotationBeanDefinitionReader
        this();


        // 2.先把类 AppConfig 注册到bean工厂里（BeanDefinition + registerBeanDefinition+ FactoryBean）
        register(componentClass);



        // 3.扫描这个路径，提取出这个路径下所有的bean，然后注册到bean工厂（单例bean的初始化）


    }

    private void register(Class<?> componentClass) {
        this.reader.register(componentClass);
    }

    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {

    }
}
