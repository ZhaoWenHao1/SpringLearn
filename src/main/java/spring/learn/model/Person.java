package spring.learn.model;

import spring.learn.annotation.MyAutowired;

/**
 * @program: SpringDemo
 * @description:
 * @author: zwh
 * @create: 2021/9/7 10:03
 **/
public class Person {
    private String name;
    private Integer age;

    @MyAutowired
    public Person(String name, Integer age) {
        System.out.println("调用 Person(String name, int age)");
        this.name = name;
        this.age = age;
    }

    @MyAutowired
    public Person() {
        System.out.println("调用 Person()");
    }


    @MyAutowired
    public Person(String name) {
        System.out.println("调用 Person(String name)");
        this.name = name;
    }



    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
