package com.mike.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * @Author: 23236
 * @createTime: 2022/7/5   20:46
 * @description: 为了代码复用所以是虚类
 * 使用反射来调用对应的函数方法
 *
 *
 *
 **/

//@WebServlet(name = "ServletUser", value = "/userbyname")
public  abstract class BaseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action=req.getParameter("action");
        System.out.println("base servlet action="+action);
        try {
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("req map="+req.getParameterMap());

        String action=req.getParameter("action");
        action="upload";
        ServletInputStream inputStream = req.getInputStream();
        byte[] bytes = new byte[10240];
        int len=inputStream.read(bytes);
        String str = new String(bytes, 0, len);
        System.out.println("before split"+str);
        int indexB = str.indexOf("name=\"action\"", 13);
        int indexE = str.lastIndexOf("------",indexB);
        System.out.println("index="+indexB+indexE);

        action=str.substring(indexB,indexE);
        System.out.println("after split action="+str);
        System.out.println("after split action="+action);
        try {
            Method method = this.getClass().getDeclaredMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this,req,resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
