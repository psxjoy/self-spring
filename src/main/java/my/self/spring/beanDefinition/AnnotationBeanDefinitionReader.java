package my.self.spring.beanDefinition;

import my.self.spring.annotation.Scope;

public class AnnotationBeanDefinitionReader {

    private BeanDefinitionRegistry registry;

    public AnnotationBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    // 注册路径扫描的 bean到 bean工厂里
    public void register(Class<?> componentClass) {
        registerBean(componentClass);
    }

    private void registerBean(Class<?> componentClass) {
        doRegisterBean(componentClass);
    }

    private void doRegisterBean(Class<?> componentClass) {
        // 把AppConfig 读成一个 beanDefinition 定义
        AnnotateGenericBeanDefinition beanDefinition
                = new AnnotateGenericBeanDefinition();
        beanDefinition.setClazz(componentClass);
        if (componentClass.isAnnotationPresent(Scope.class)) {
            String scope = componentClass.getAnnotation(Scope.class).value();
            beanDefinition.setScope(scope);
        } else {
            beanDefinition.setScope("singleton");
        }
        // beanDefinition 创建完成以后，给 beanFactory 进行bean注册
        BeanDefinitionReaderUtils.registerBeanDefinition(beanDefinition, this.registry);

    }
}
