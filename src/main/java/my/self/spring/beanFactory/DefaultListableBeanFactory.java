package my.self.spring.beanFactory;

import my.self.spring.beanDefinition.AnnotateBeanDefinition;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;

public class DefaultListableBeanFactory  implements BeanDefinitionRegistry, BeanFactory{
    //只有bean注册上以后，才能有  getBean
    public Object getBean(String beanName) {
        return null;
    }

    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {

    }
}
