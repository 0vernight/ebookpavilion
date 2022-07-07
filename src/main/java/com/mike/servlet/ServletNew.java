package com.mike.servlet; /**
 * @Author: 23236
 * @createTime: 2022/6/19   12:15
 * @description: ${Description}
 **/

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "servletNew", value = "/servletNew")
public class ServletNew extends HttpServlet {           //new servlet出来的自带注解
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("in the ServletNew="+parameterMap);
        System.out.println("username="+request.getParameter("username"));
        System.out.println("modul username="+request.getParameter("username"));

        //给请求里放入数据
        request.setAttribute("msg","ok servlet from A");
        request.setAttribute("svc","servletNew");

//        //请求转发,查找
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/pages/main/registration.html");
//        //前进
//        requestDispatcher.forward(request,response);
//        //请求转发,查找
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/toRegister");
//        //前进
//        requestDispatcher.forward(request,response);
//        //请求转发,查找
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/servletTest");
        //前进
        requestDispatcher.forward(request,response);

//        response.sendRedirect("/servletTest");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> parameterMap = request.getParameterMap();
        System.out.println("in the ServletNewPost="+parameterMap);
        System.out.println("username="+request.getParameter("username"));
        System.out.println("modul username="+request.getParameter("username"));
    }
}
