package com.mike.DAO;

import com.mike.bean.User;

import java.util.List;

/**
 * @Author: 23236
 * @createTime: 2022/6/23   19:46
 * @description:
 **/
public interface UserDao {

    //返回null说明没有
    public List<User> queryByUserName(String username);

    //返回0说明没有
    public int saveUser(User user);

    //返回null说明没有
    public User queryByUsernamePassword(String username,String password);


}
