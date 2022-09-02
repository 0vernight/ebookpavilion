package com.mike.servlet;

import com.google.code.kaptcha.servlet.KaptchaServlet;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: 23236
 * @createTime: 2022/7/17   13:32
 * @description: 验证码
 **/

@RequestMapping("/kaptcha")
@WebServlet(name = "kaptchaservlet", value = "/kaptcha")
public class MyKaptcha extends KaptchaServlet {
//    KaptchaServlet kaptchaServlet=new KaptchaServlet();
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("MyKaptcha 类-》进到kaptcha里了");
        super.doGet(req,resp);
    }
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }


}
