package my.self.spring.beanFactory;

import my.self.spring.beanDefinition.AnnotateBeanDefinition;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultListableBeanFactory implements BeanFactory, BeanDefinitionRegistry {

    private final Map<String, AnnotateBeanDefinition> beanDefinitionMap =
            new ConcurrentHashMap<String, AnnotateBeanDefinition>(256);

    @Override
    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {
        //源码里有一些逻辑
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    //只有bean都注册上以后，才能有 getBean
    @Override
    public Object getBean(String beanName) {
        return null;
    }
}
