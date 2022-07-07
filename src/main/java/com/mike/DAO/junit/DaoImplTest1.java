package com.mike.DAO.junit;

import com.mike.DAO.Impl.CustomerImpl;
import com.mike.DAO.Impl.UserDaoImpl;
import com.mike.bean.Customer;
import com.mike.bean.User;
import org.junit.Test;

import java.util.List;

/**
 * @Author: 23236
 * @createTime: 2022/5/29   23:01
 * @description: 测试类
 **/
public class DaoImplTest1 {

    CustomerImpl customer=new CustomerImpl();
    UserDaoImpl userimpl=new UserDaoImpl();

    @Test
    public void testUser(){
        List<User> mike = userimpl.queryByUserName("mike");
        System.out.println(mike);
    }
    @Test
    public void testCustomerall(){
        List<Customer> customers = customer.queryAll();
        System.out.println("customerTest="+customers);
    }
}
