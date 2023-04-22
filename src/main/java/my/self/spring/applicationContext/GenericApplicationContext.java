package my.self.spring.applicationContext;

import my.self.spring.beanDefinition.AnnotateBeanDefinition;
import my.self.spring.beanDefinition.BeanDefinitionRegistry;
import my.self.spring.beanFactory.DefaultListableBeanFactory;

public class GenericApplicationContext implements BeanDefinitionRegistry {

    private DefaultListableBeanFactory beanFactory;

    public GenericApplicationContext() {
        this.beanFactory = new DefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, AnnotateBeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    public void refresh() {
        // 获取 beanFactory
        DefaultListableBeanFactory beanFactory = obtainBeanFactory();
        // 把 appConfig 路径下的所有的 bean 进行扫描，注册到 bean工厂 beanDefinition（UserService and UserService1）
        invokeBeanFactoryPostProcessors(beanFactory);
        // 初始化 beanDefinition 所代表的单例 bean，放到单例 bean 的容器（缓存）里
        finishBeanFactoryInitilization(beanFactory);
    }

    private void finishBeanFactoryInitilization(DefaultListableBeanFactory beanFactory) {
        this.beanFactory.preInstantiateSingleton();
    }

    private void invokeBeanFactoryPostProcessors(DefaultListableBeanFactory beanFactory) {
        beanFactory.doScan();
    }

    private DefaultListableBeanFactory obtainBeanFactory() {
        return this.beanFactory;
    }

    public Object getBean(String s) {
       return  this.beanFactory.getBean(s);
    }
}
