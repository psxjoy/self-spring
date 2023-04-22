# self-spring

## 1 想要书写简易的 Spring 源码，首先要搞明白 BeanFactory,BeanDefinition,ApplicationContext 三者之间的关系

```BeanFactory```:工厂，生产bean，提供获取bean的方法，getBean方法。 -> 如果要生产bean，必须要解析注解 @Service ，一个bean可能是单例的，
也可能是多例的

```BeanDefinition```:bean定义（也就是生产bean的原料）。String scope-> （单例 singleton，多例 prototype）;Class clazz
代表当前bean属于哪个class

```ApplicationContext```:容器（上下文）.主导 BeanDefinition 的生成，把 BeanDefinition
***传递（注册，beanDefinition 注册，beanDefinitionRegister （方法：registerBeanDefinition））***给 BeanFactory 生成bean。

getBean 方法，最终调用的是BeanFactory 的 getBean。

Resitry
beanDefinition | beanDefinition
left:context right:beanfactory
