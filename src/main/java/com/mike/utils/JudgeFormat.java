package com.mike.utils;

import com.mike.bean.User;

/**
 * @author: 23236
 * @date: 2021/4/17 19:36
 * @description:
 */


public class JudgeFormat {
    User user;

    public JudgeFormat(User user) {
        this.user = user;
    }

    public boolean judegeUser(){

        if (user.getUsername()==""||user.getPassword()==""||user.getEmail()==""){
            return false;
        }
        else if (user.getPassword().length()<4||user.getPassword().length()>14){
            return false;
        }

        return true;
    }
}
