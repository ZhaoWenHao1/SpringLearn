package spring.learn.test;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.*;
import spring.learn.model.Car;
import spring.learn.model.CompositeObj;
import spring.learn.model.User;

import java.util.Arrays;

/**
 * @program: SpringDemo
 * @description:
 * @author: zwh
 * @create: 2021/9/6 15:17
 **/
public class SpringTestStage1 {

    /**
     * 案例1：组装一个简单的bean
     */
    public void test1(){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Car.class);
        BeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        System.out.println(beanDefinition);
    }

    /**
     * 案例2：组装一个有属性的bean
     */
    public void test2(){
        // 指定class
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(Car.class);
        // 设置普通类型属性
        beanDefinitionBuilder.addPropertyValue("name", "奥迪");
        // 获取BeanDefinition
        BeanDefinition carBeanDefinition = beanDefinitionBuilder.getBeanDefinition();
        System.out.println(carBeanDefinition);

        // 创建spring容器
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        // 调用registerBeanDefinition向容器中注册bean
        factory.registerBeanDefinition("car", carBeanDefinition);
        Car bean = factory.getBean("car", Car.class);
        System.out.println(bean);
    }




    /**
     * 案例3：组装一个有依赖关系的bean
     * 下面代码等效于：
     *
     * <bean id="car" class="com.javacode2018.lesson002.demo1.Car">
     *     <property name="name" value="奥迪"/>
     * </bean>
     * <bean id="user" class="com.javacode2018.lesson002.demo1.User">
     *     <property name="name" value="路人甲Java"/>
     *     <property name="car" ref="car"/>
     * </bean>
     */
    public void test3(){
        // 先创建car这个BeanDefinition
        BeanDefinition carBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(Car.class.getName())
                .addPropertyValue("name", "奥迪")
                .getBeanDefinition();

        // 创建User这个BeanDefinition
        BeanDefinition userBeanDefinition = BeanDefinitionBuilder.rootBeanDefinition(User.class.getName()).
                addPropertyValue("name", "路人甲Java").
                addPropertyReference("car", "car"). //@1
                getBeanDefinition();

        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("car", carBeanDefinition);
        factory.registerBeanDefinition("user", userBeanDefinition);
        System.out.println(factory.getBean("car"));
        System.out.println(factory.getBean("user"));
    }




    /**
     * 案例4：来2个有父子关系的bean
     * 下面代码等价于：
     * <bean id="car1" class="com.javacode2018.lesson002.demo1.Car">
     *     <property name="name" value="保时捷"/>
     * </bean>
     * <bean id="car2" parent="car1" />
     */
    public void test4(){
        //先创建car这个BeanDefinition
        BeanDefinition carBeanDefinition1 = BeanDefinitionBuilder.
                genericBeanDefinition(Car.class).
                addPropertyValue("name", "保时捷").
                getBeanDefinition();

        BeanDefinition carBeanDefinition2 = BeanDefinitionBuilder.
                genericBeanDefinition(). //内部生成一个GenericBeanDefinition对象
                setParentName("car1"). //@1：设置父bean的名称为car1
                getBeanDefinition();

        //创建spring容器
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        //调用registerBeanDefinition向容器中注册bean
        //注册car1->carBeanDefinition1
        factory.registerBeanDefinition("car1", carBeanDefinition1);
        //注册car2->carBeanDefinition2
        factory.registerBeanDefinition("car2", carBeanDefinition2);
        //从容器中获取car1
        System.out.println(String.format("car1->%s", factory.getBean("car1")));
        //从容器中获取car2
        System.out.println(String.format("car2->%s", factory.getBean("car2")));
    }


    /**
     * 案例5：通过api设置（Map、Set、List）属性
     *
     * ManagedList：属性如果是List类型的，t需要用到这个类进行操作，这个类继承了ArrayList
     * ManagedSet：属性如果是Set类型的，t需要用到这个类进行操作，这个类继承了LinkedHashSet
     * ManagedMap：属性如果是Map类型的，t需要用到这个类进行操作，这个类继承了LinkedHashMap
     */
    public void test5(){
        //定义car1
        BeanDefinition car1 = BeanDefinitionBuilder.
                genericBeanDefinition(Car.class).
                addPropertyValue("name", "奥迪").
                getBeanDefinition();
        //定义car2
        BeanDefinition car2 = BeanDefinitionBuilder.
                genericBeanDefinition(Car.class).
                addPropertyValue("name", "保时捷").
                getBeanDefinition();

        //定义CompositeObj这个bean
        //创建stringList这个属性对应的值
        ManagedList<String> stringList = new ManagedList<>();
        stringList.addAll(Arrays.asList("java高并发系列", "mysql系列", "maven高手系列"));

        //创建carList这个属性对应的值,内部引用其他两个bean的名称[car1,car2]
        ManagedList<RuntimeBeanReference> carList = new ManagedList<>();
        carList.add(new RuntimeBeanReference("car1"));
        carList.add(new RuntimeBeanReference("car2"));

        //创建stringList这个属性对应的值
        ManagedSet<String> stringSet = new ManagedSet<>();
        stringSet.addAll(Arrays.asList("java高并发系列", "mysql系列", "maven高手系列"));

        //创建carSet这个属性对应的值,内部引用其他两个bean的名称[car1,car2]
        ManagedList<RuntimeBeanReference> carSet = new ManagedList<>();
        carSet.add(new RuntimeBeanReference("car1"));
        carSet.add(new RuntimeBeanReference("car2"));

        //创建stringMap这个属性对应的值
        ManagedMap<String, String> stringMap = new ManagedMap<>();
        stringMap.put("系列1", "java高并发系列");
        stringMap.put("系列2", "Maven高手系列");
        stringMap.put("系列3", "mysql系列");

        ManagedMap<String, RuntimeBeanReference> stringCarMap = new ManagedMap<>();
        stringCarMap.put("car1", new RuntimeBeanReference("car1"));
        stringCarMap.put("car2", new RuntimeBeanReference("car2"));


        //下面我们使用原生的api来创建BeanDefinition
        GenericBeanDefinition compositeObj = new GenericBeanDefinition();
        compositeObj.setBeanClassName(CompositeObj.class.getName());
        compositeObj.getPropertyValues().add("name", "路人甲Java").
                add("salary", 50000).
                // RuntimeBeanReference：用来表示bean引用类型，类似于xml中的ref
                        add("car1", new RuntimeBeanReference("car1")).
                add("stringList", stringList).
                add("carList", carList).
                add("stringSet", stringSet).
                add("carSet", carSet).
                add("stringMap", stringMap).
                add("stringCarMap", stringCarMap);

        //将上面bean 注册到容器
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.registerBeanDefinition("car1", car1);
        factory.registerBeanDefinition("car2", car2);
        factory.registerBeanDefinition("compositeObj", compositeObj);

        //下面我们将容器中所有的bean输出
        for (String beanName : factory.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, factory.getBean(beanName)));
        }
    }

    public static void main(String[] args) {
        SpringTestStage1 test = new SpringTestStage1();
        test.test3();
    }
}
