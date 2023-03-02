package my.self.spring.beanDefinition;

public class AnnotateGeniricBeanDefinition implements AnnotateBeanDefinition{
    private Class clazz;

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    private String scope;
}
