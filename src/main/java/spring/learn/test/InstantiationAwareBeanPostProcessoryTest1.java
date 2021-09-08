package spring.learn.test;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.lang.Nullable;
import spring.learn.model.UserModel;

/**
 * @program: SpringDemo
 * @description:
 * @author: zwh
 * @create: 2021/9/7 17:09
 **/
public class InstantiationAwareBeanPostProcessoryTest1 {

    @Test
    public void test1(){
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();
        factory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() {
            @Override
            public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
                if("user1".equals(beanName)){
                    return false;
                }else {
                    return true;
                }
            }
        });
        factory.registerBeanDefinition("user1", BeanDefinitionBuilder
                .genericBeanDefinition(UserModel.class)
                .addPropertyValue("name", "路人甲Java")
                .addPropertyValue("age", 30)
                .getBeanDefinition());

        factory.registerBeanDefinition("user2", BeanDefinitionBuilder
                .genericBeanDefinition(UserModel.class)
                .addPropertyValue("name", "刘德华")
                .addPropertyValue("age", 50)
                .getBeanDefinition());

        for (String beanDefinitionName : factory.getBeanDefinitionNames()) {
            System.out.println(String.format("%s -> %s", beanDefinitionName, factory.getBean(beanDefinitionName)));
        }
    }

    @Test
    public void test3() {
        DefaultListableBeanFactory factory = new DefaultListableBeanFactory();

        factory.addBeanPostProcessor(new InstantiationAwareBeanPostProcessor() { // @0
            @Nullable
            @Override
            public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
                if ("user1".equals(beanName)) {
                    if (pvs == null) {
                        pvs = new MutablePropertyValues();
                    }
                    if (pvs instanceof MutablePropertyValues) {
                        MutablePropertyValues mpvs = (MutablePropertyValues) pvs;
                        //将姓名设置为：路人
                        mpvs.add("name", "路人");
                        //将年龄属性的值修改为18
                        mpvs.add("age", 18);
                    }
                }
                return null;
            }
        });

        //注意 user1 这个没有给属性设置值
        factory.registerBeanDefinition("user1", BeanDefinitionBuilder.
                genericBeanDefinition(UserModel.class).
                getBeanDefinition()); //@1

        factory.registerBeanDefinition("user2", BeanDefinitionBuilder.
                genericBeanDefinition(UserModel.class).
                addPropertyValue("name", "刘德华").
                addPropertyValue("age", 50).
                getBeanDefinition());

        for (String beanName : factory.getBeanDefinitionNames()) {
            System.out.println(String.format("%s->%s", beanName, factory.getBean(beanName)));
        }
    }


}
