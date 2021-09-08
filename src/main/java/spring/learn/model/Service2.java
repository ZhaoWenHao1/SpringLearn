package spring.learn.model;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @program: SpringDemo
 * @description:
 * @author: zwh
 * @create: 2021/9/6 18:39
 **/
public class Service2 {
    @Autowired
    private Service1 service1; // 标注了@Autowired，说明需要注入这个对象

    @Override
    public String toString() {
        return "Service2{" +
                "service1=" + service1 +
                '}';
    }
}
