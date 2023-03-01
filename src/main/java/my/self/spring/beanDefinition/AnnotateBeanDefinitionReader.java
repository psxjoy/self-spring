package my.self.spring.beanDefinition;

import my.self.spring.annotation.Scope;

public class AnnotateBeanDefinitionReader {
    //注册 路径扫描bean到bean工厂里
    public void register(Class<?> componentClass) {
        registerBean(componentClass);
    }

    private void registerBean(Class<?> componentClass) {
        doRegisterBean(componentClass);
    }

    private void doRegisterBean(Class<?> componentClass) {
        // 把 AppConfig 读成一个 BeanDefinition 定义
        AnnotateGeniricBeanDefinition beanDefinition = new AnnotateGeniricBeanDefinition();
        beanDefinition.setClazz(componentClass);
        if (componentClass.isAnnotationPresent(Scope.class)) {
            String scope = componentClass.getAnnotation(Scope.class).value();
            beanDefinition.setScope(scope);
        }else {
            beanDefinition.setScope("singleton");
        }

        // beanDefinition 创建完成以后，给 beanFactory 进行bean注册
    }
}
