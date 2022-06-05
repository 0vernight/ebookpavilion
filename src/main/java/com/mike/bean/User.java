package com.mike.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author: 23236
 * @date: 2021/3/15 15:57
 * @description:@Data是lombok 表达式方便构造getter，setter，构造器
 * @ConfigurationProperties在yml 或properties文件里设置是使用
 *
 */
@Component

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)

@ConfigurationProperties(prefix = "user")
public class User {
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
