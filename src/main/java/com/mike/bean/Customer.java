package com.mike.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: 23236
 * @createTime: 2022/3/8   21:46
 * @description: 对应数据库当中的customer类
 *
 * lombok 可以学一学跟着mybatis
 *
 **/
@Component


@AllArgsConstructor
//@NoArgsConstructor
@ToString
//@Accessors(chain = true)
@ConfigurationProperties(prefix = "customer")

@Data
public class Customer<T>  implements Serializable {
    public static final long serialVersionUID = 42544573757L;//每个序列化的类必须给出序列化的版本
    T type;
    public int customerId;
    public int storeId;
    public int active;
    public String firstName ;
    public String lastName ;
    public int age ;
    public String email;
    public int addressId;
    public String address;
    public String password;
    public Date createDate;
    public Date lastDate;

    public Customer() {
    }

    public Customer(T type, String firstName, String lastName, int age, String email) {
        this.type = type;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }
    public Customer(String firstName, String lastName, int age, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }
    public Customer(int customerId, String firstName, String lastName, int age, String email) {
        this.customerId=customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
    }
    public Customer(String firstName, String lastName, int age, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.address=address;
    }


}
