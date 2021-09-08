package spring.learn.annotation;

import java.lang.annotation.*;

/**
 * @program: SpringDemo
 * @description:
 * @author: zwh
 * @create: 2021/9/7 10:01
 **/
@Target(ElementType.CONSTRUCTOR)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyAutowired {
}
