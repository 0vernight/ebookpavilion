package com.mike.controller;

import com.mike.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 23236
 * @createTime: 2022/11/29   14:23
 * @description: 专门用来测试socket的控制器
 **/


@RestController("/chat/{name}")
//@ServerEndpoint("/chat/{name}")

public class SocketController {

    @Autowired
    User user;
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());


    @RequestMapping("/chat/{parm")
    @ResponseBody   //been 有user用的所以可以映射
//    @RequestBody  //因为前端没给user实体类所不能映射？
    public void chat(@PathVariable(value = "fromUser" ,required = false)String name,User user,
                     HttpServletRequest request, HttpServletResponse response,
                     Model model) throws IOException, ServletException {
//        获取路径参数
//        方法上加@RequestBody @ResponseBody
//        @PathParam(value = "name")String name//null
//        @PathVariable(value = "name" ,required = false)//可以获取
//        @RequestParam(value = "name" ,required = false,defaultValue = "")//不行
        System.out.println("url:"+request.getRequestURL());
        System.out.println("url:"+request.getRequestURI());
        System.out.println("进入了测试，聊天人："+name);
        System.out.println("进入了测试，聊天人1："+user.getUsername());
        System.out.println("即将要调用WebSocketConfig的ws-chat");
//        不知道为什么重定向不能进行
//        response.sendRedirect("/test1");
        System.out.println(request.getSession().getAttributeNames().toString());
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/ws-chat/"+name);
        requestDispatcher.forward(request,response);
    }


}



//@ServerEndpoint
//通过这个 spring boot 就可以知道你暴露出去的 ws 应用的路径，有点类似我们经常用的@RequestMapping。比如你的启动端口是8080，而这个注解的值是ws，那我们就可以通过 ws://127.0.0.1:8080/ws 来连接你的应用
//@OnOpen
//当 websocket 建立连接成功后会触发这个注解修饰的方法，注意它有一个 Session 参数
//@OnClose
//当 websocket 建立的连接断开后会触发这个注解修饰的方法，注意它有一个 Session 参数
//@OnMessage
//当客户端发送消息到服务端时，会触发这个注解修改的方法，它有一个 String 入参表明客户端传入的值
//@OnError
//当 websocket 建立连接时出现异常会触发这个注解修饰的方法，注意它有一个 Session 参数
//        另外一点就是服务端如何发送消息给客户端，服务端发送消息必须通过上面说的 Session 类，通常是在@OnOpen 方法中，当连接成功后把 session 存入 Map 的 value，key 是与 session 对应的用户标识，当要发送的时候通过 key 获得 session 再发送，这里可以通过 session.getBasicRemote_().sendText(_) 来对客户端发送消息。


@ServerEndpoint(value = "/chatting/2")
@Component
class WebSocketServerThread {

//    private Socket socket;
    private static final Map<String,Session> sessionMap =new ConcurrentHashMap<>();

//    public WebSocketServerThread(Socket socket) {
//        this.socket = socket;
//    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username")String username){
        System.out.println("onOpen ="+username);
    }
//    @Override
//    public void run() {
//        InputStream in = null;
//        OutputStream out = null;
//        try {
//            in = socket.getInputStream();
//            out = socket.getOutputStream();
//            Integer sourcePort = socket.getPort();
//            int maxLen = 2048;
//            byte[] contextBytes = new byte[maxLen];
//            int realLen;
//            StringBuffer message = new StringBuffer();
//            BIORead:
//            while (true) {
//                try {
//                    while ((realLen = in.read(contextBytes, 0, maxLen)) != -1) {
//                        message.append(new String(contextBytes, 0, realLen));
//                        /*
//                         * 我们假设读取到“over”关键字，
//                         * 表示客户端的所有信息在经过若干次传送后，完成
//                         * */
//                        if (message.indexOf("over") != -1) {
//                            break BIORead;
//                        }
//                    }
//
//                    //下面打印信息
////                    log.info("服务器(收到来自于端口：" + sourcePort + "的信息：" + message);
//                    //下面开始发送信息
//                    out.write("回发响应信息！".getBytes());
//                    //关闭
//                    out.close();
//                    in.close();
//                    this.socket.close();
//                } catch (Exception e) {
//
//                    throw new RuntimeException(e);
//                } finally {
//                    new Throwable();
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
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
