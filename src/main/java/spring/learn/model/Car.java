package spring.learn.model;

/**
 * @program: SpringDemo
 * @description:
 * @author: zwh
 * @create: 2021/9/6 15:16
 **/
public class Car {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                '}';
    }
}
