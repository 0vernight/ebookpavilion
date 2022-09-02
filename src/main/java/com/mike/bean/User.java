package com.mike.bean;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: 23236
 * @date: 2021/3/15 15:57
 * @description:@Data是lombok 表达式方便构造getter，setter，构造器
 * @ConfigurationProperties在yml 或properties文件里设置是使用
 *
 */
@Component      //它的作用就是实现bean的注入

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode
//@Accessors(chain = true)//放开以后jdbc连接池获取数据全是null值//开起chain=true后可以使用链式的set
//        User user=new User().setAge(27).setName("kevin");//返回对象

@ConfigurationProperties(prefix = "user")
public class User<T>  implements Serializable {
    public static final long serialVersionUID = 42414573127L;//每个序列化的类必须给出序列化的版本
    private String id;
    private String username;
    private String nickname;
    private String password;
    private String email;
    private String phone;
    private int age;
    private String sex;
    private long creationDate;
    private String avatar;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDay;
    private int contribution;
    private String address;
    private int status;

}
