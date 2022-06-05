package com.mike.controller;

import com.mike.DTO.BaseResponse;
import com.mike.bean.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: 23236
 * @date: 2021/4/16 18:59
 * @description:
 */


@RestController
public class TestUserController {

    @RequestMapping("/t_register")
    public BaseResponse<User> register(User user, HttpServletRequest request) {

        System.out.println(user);
//        userService.register(user);
         BaseResponse<User> response=new BaseResponse<>();
         response.setCode(200)
                 .setMessage("在这种")
                 .setData(user);
         return response;
    }

}
