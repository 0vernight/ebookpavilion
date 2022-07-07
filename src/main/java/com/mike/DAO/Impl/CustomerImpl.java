package com.mike.DAO.Impl;

import com.mike.DAO.BaseDAO;
import com.mike.DAO.CustomerDAO;
import com.mike.bean.Customer;

import java.sql.Connection;
import java.util.List;

/**
 * @Author: 23236
 * @createTime: 2022/5/29   22:26
 * @description: customer 类的实现类
 **/
public class CustomerImpl extends BaseDAO<Customer> implements CustomerDAO {
//    private CustomerImpl(){
//
//    }

//    {
//        Type genericSuperclass = this.getClass().getGenericSuperclass();
//        ParameterizedType parameterizedType= (ParameterizedType) genericSuperclass;
//        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();//获取了父类 的泛型参数
//        Type clazz = actualTypeArguments[0];
//    }
    @Override
    public void insert(Connection connection, Customer customer) {

        String sql="insert into customer (first_name,last_name,age,email,address) values(?,?,?,?,?)";
        update(connection,sql,customer.firstName,customer.lastName,customer.age,customer.email,customer.address);
    }

    @Override
    public int deleteById(Connection connection, int id) {

        String sql="delete from customer where customer_id=?";

        return update(connection,sql,id);
    }

    @Override
    public int updateById(Connection connection, Customer customer) {
        String sql="update customer set first_name=?,last_name=?,email=?,customer_id=?";

        return update(connection,sql,customer.firstName,customer.lastName,customer.email,customer.customerId);
    }

    @Override
    public Customer getCustomers(Connection connection, int id) {
        String sql="select  first_name firstName,last_name lastName,email,address_id addressId from customer where customer_id=?";

        return getInstance(connection,Customer.class,sql,id);

    }

    @Override
    public List<Customer> getAll(Connection connection) {
        String sql="select  first_name firstName,last_name lastName,email,address_id addressId from customer ";

        return queryList(connection,Customer.class,sql);
    }

    @Override
    public long getCount(Connection connection) {
        String sql="select count(1) from customer  ";

        return getValue(connection,sql);
    }

    @Override
    public List<Customer> queryAll() {
        String sql="select  first_name firstName,last_name lastName,email,address_id addressId from customer where customer.first_name=? ";
        List<Customer> list = queryAll(Customer.class, sql, "mike");
        System.out.println("customerImpl="+list);
        return list;
    }
}
