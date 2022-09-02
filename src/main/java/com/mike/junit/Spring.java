package com.mike.junit;

import com.mike.bean.User;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author: 23236
 * @createTime: 2022/8/1   21:23
 * @description: 在学习spring时进行的测试
 **/
public class Spring {
    @Test
    public  void userXmlSpring(){
//        1.加载spring配置文件，BeanFactory的子接口，面向开发者，在这里创建
        ApplicationContext context=new ClassPathXmlApplicationContext("mybatis/mapper/userbean.xml");

        //也可以使用beanfactory 来加载配置文件,spring内部使用的接口
        BeanFactory beanFactory=new ClassPathXmlApplicationContext("mybatis/mapper/userbean.xml");

//        2.获取配置创建对象
        User user = context.getBean("user", User.class);

        //在这里创建
        User user1 = beanFactory.getBean("user", User.class);
        System.out.println(user == user1);

//        3。使用对象
        System.out.println(user);
    }
}
