package com.mike.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mike.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 23236
 * @createTime: 2022/12/15   22:30
 * @description:
 **/

//value必须语config里的变量对应
@Service("loginUserDetailsService")
public class LoginUserDetailsService implements UserDetailsService {

    @Autowired
    com.mike.bean.User user;
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<GrantedAuthority> auths = AuthorityUtils.commaSeparatedStringToAuthorityList("role");
        System.out.println("loginuserDetailsService进来了");

        QueryWrapper<com.mike.bean.User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*");
        queryWrapper.eq("username", s);
        List<Map<String, Object>> maps = userMapper.selectMaps(queryWrapper);
        System.out.println("查询第一次");
        maps.forEach(System.out::println);
        queryWrapper.eq("username", s);
        List<com.mike.bean.User> user1 = userMapper.selectList(queryWrapper);
        System.out.println("第二次查询：" + user1);

//        if (user1 == null) {
//            throw new UsernameNotFoundException("用户名不存在！");
//        }
        if (maps == null) {
            System.out.println("maps is null");
        }
        if (user1.isEmpty()) {
            System.out.println("user1 is empty");
        }

        user.setUsername(s);
        System.out.println("输出user看看" + user);

        List<com.mike.bean.User> list = userMapper.selectByname(user);
        System.out.println("第三次查询：" + list);
        System.out.println(auths);
        if (list.size() > 0) {
            user = list.get(0);
        }


        return new User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), auths);
    }
}
