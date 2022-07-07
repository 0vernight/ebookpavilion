package com.mike.DAO.Impl;

import com.mike.DAO.BaseDAO;
import com.mike.DAO.UserDao;
import com.mike.bean.User;

import java.util.List;

/**
 * @Author: 23236
 * @createTime: 2022/6/23   19:58
 * @description:
 **/
public class UserDaoImpl extends BaseDAO<User> implements UserDao {
    @Override
    public List<User> queryByUserName(String username) {
//        String sql="select username ,email ,age  from user where username=?";
        String sql="select *  from user where username=?";
        List<User> list = queryAll(User.class, sql, username);
        System.out.println("userImpl="+list);
        return list;
    }

    @Override
    public int saveUser(User user) {
        String sql="\"insert into user (id, username,nickname, password, email, phone, age, sex, creation_date, birthday, address) values(?,?,?,?,?,?,?,?,?,?,?)";
        return updateDruid(User.class, sql, user.getId(),user.getUsername(),user.getNickname());    //参数没写完
    }

    @Override
    public User queryByUsernamePassword(String username, String password) {
        String sql="select * from user where user.username=? and user.password=?";
        List list = queryAll(User.class, sql, username,password);
        return (User) list.get(0);
    }
}
