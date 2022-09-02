package com.mike.DAO.junit;

import com.mike.DAO.Impl.UserDaoImpl;
import com.mike.bean.User;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @Author: 23236
 * @createTime: 2022/6/24   22:06
 * @description:
 **/
class UserDaoTest {
    UserDaoImpl userdi=new UserDaoImpl();

    @Test
    List<User> queryByUserName(String username) {
        List<User> mike = userdi.queryByUserName("mike");
        System.out.println(mike);
        return null;
    }

    @Test
    void saveUser() {
    }

    @Test
    void queryByUsernamePassword() {
    }

}