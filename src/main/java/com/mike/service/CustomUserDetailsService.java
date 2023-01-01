package com.mike.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mike.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @Author: 23236
 * @createTime: 2022/12/20   14:29
 * @description: 用户验证的实现类
 **/


//@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private User user;

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        // 根据用户名验证用户
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(User::getUsername,s);

        User User = userService.searchByName(user).getData();
        if (User == null) {
            throw new UsernameNotFoundException("用户名不存在，登陆失败。");
        }

        // 构建UserDetail对象
        System.out.println("CustomUserDetailsService进来了，什么都没做出去了");


        return null;

    }
}
