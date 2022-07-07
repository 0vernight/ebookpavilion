package com.mike.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Author: 23236
 * @createTime: 2022/6/19   12:04
 * @description: servlet practicing
 **/

@WebServlet(name = "servletTest", value = "/servletTest")
public class servletTest extends HttpServlet {              //new class出来的不带注解，后面手动加上去的

    public servletTest() {
        super();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doGet(req, resp);
        //write own code
        System.out.println("servletTest");
        resp.addDateHeader("code",200);
        resp.addHeader("username","username");

        String msg = (String) req.getAttribute("msg");
        String svc = (String) req.getAttribute("svc");
        System.out.println("a given to b="+msg+"  ->"+svc);

        //返回给网页不设置字符集必定会出现乱码
        resp.setCharacterEncoding("utf-8");
        resp.setHeader("Content-Type","text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.write("all kind of things good for you,我爱我的祖国，我爱我的家，  مەنمۈ ئۇيگە قايتقۇمباتتى .");
//        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/toRegister");
//        requestDispatcher.forward(req,resp);

        resp.sendRedirect("/index.html");
//        //可以访问servlet但是不能访问受保护的资源
//        resp.sendRedirect("/toRegister");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //super.doPost(req, resp);
        //write own code here
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        System.out.println(username);

    }


}
