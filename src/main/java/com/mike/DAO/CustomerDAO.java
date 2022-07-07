package com.mike.DAO;


import com.mike.bean.Customer;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: 23236
 * @createTime: 2022/5/29   22:15
 * @description: 此接口用于规范针对customer表的常用操作
 *
 * 要知道接口到底是干什么的，怎那么用
 **/
public interface CustomerDAO {

    void insert(Connection connection, Customer customer);

    int deleteById(Connection connection,int id);

    int updateById(Connection connection,Customer customer);

    Customer getCustomers(Connection connection,int id);

    List<Customer> getAll(Connection connection);

    long getCount(Connection connection);

    List<Customer> queryAll();
}
