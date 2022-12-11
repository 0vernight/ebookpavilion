package com.mike.controller;

import com.mike.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.WebSocketSession;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author: 23236
 * @createTime: 2022/11/29   14:23
 * @description: 专门用来测试socket的控制器
 **/


@RestController("/chat")
//@ServerEndpoint("/chat/{name}")

public class SocketController {

    @Autowired
    User user;
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());


    @RequestMapping("/chat/{parm}")
    @ResponseBody   //been 有user用的所以可以映射
//    @RequestBody  //因为前端没给user实体类所不能映射？
    public void chat(@PathVariable("parm") String parm, @PathParam(value = "username") String username,
                     @PathParam(value = "toUsername") String toUsername, User user, HttpSession session,
                     HttpServletRequest request, HttpServletResponse response,
                     Model model) throws IOException, ServletException {
//        获取路径参数
//        方法上加@RequestBody @ResponseBody
//        @PathParam(value = "name")String name//null
//        @PathVariable(value = "name" ,required = false)//可以获取
//        @RequestParam(value = "name" ,required = false,defaultValue = "")//不行
        System.out.println("url:"+request.getRequestURL());
        System.out.println("url:"+request.getRequestURI());
        System.out.println("进入了测试，聊天人："+username+parm+toUsername);
        System.out.println("进入了测试，聊天人1："+user.getUsername());
        System.out.println("即将要调用WebSocketConfig的ws-chat");
//        不知道为什么重定向不能进行
//        response.sendRedirect("/test1");
        Map<String, String[]> parameterMap = request.getParameterMap();
        request.setAttribute("uri",request.getRequestURI());
        request.setAttribute("username",username);
        request.setAttribute("username",username+1);
        request.setAttribute("toUsername",toUsername);

        System.out.println(request.getSession().getAttributeNames().toString());

        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ws-chat/"+username);
        requestDispatcher.forward(request,response);
    }
}




//    @Autowired
//    Book book;
//    @Autowired
//    Shelf shelf;
//    @Autowired
//    Comment comment;
//
//    @Autowired
//    UserService userService;
//    @Autowired
//    BookService bookService;
//    @Autowired
//    ShelfService shelfService;
//    @Autowired
//    CommentService commentService;


//    @RequestMapping({"/socketconnet"})
//    public String index(Socket webSocket , HttpServletRequest request, @RequestParam(value = "pageNum",defaultValue = "1")
//    int pageNum, @RequestParam(value = "pageSize",defaultValue = "3") int pageSize, Model model) {
//        User user = (User) request.getSession().getAttribute("user");
//        if (user == null) {
//            user = new User();
//        }
//        System.out.println("显示socket内容"+webSocket.toString());
//
//        return " ";
//    }
