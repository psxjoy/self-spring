# self-spring
想要书写简易的 Spring 源码，首先要搞明白 BeanFactory ,BeanDefinition,ApplicationContext 三者之间的关系

`BeanFactory`：生产bean,提供获取bean的方法，getBean的方法->需要解析@Service。一个bean可能是单例的，也可能是多例的。
    
`BeanDefinition`：生产 bean的原料。bean定义。String scope（单例 singleton，多例 protoype）；Class clazz代表当前bean属于哪个class
 
`ApplicationContext`：容器(上下文)。 主导BeanDefinition 的生成，把 BD 传递(注册 or beandefinition 注册，beanDefinitionRegister (方法 registerBeanDefinition)) 给BeanFactory生成Bean 
