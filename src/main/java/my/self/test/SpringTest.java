package my.self.test;

import my.self.spring.applicationContext.AnnotationConfigApplicationContext;
import my.self.test.config.AppConfig;

public class SpringTest {


    public static void main(String[] args) {
        // 创建ApplicationContext(注解形式)
        AnnotationConfigApplicationContext applicationContext =
                new AnnotationConfigApplicationContext(AppConfig.class);
        // 调用 getBean();
//        applicationContext.getbean("");
    }
}
