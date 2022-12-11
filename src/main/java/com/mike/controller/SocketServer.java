package com.mike.controller;

import org.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 23236
 * @createTime: 2022/12/4   22:43
 * @description:
 * @ServerEndpoint 通过这个 spring boot 就可以知道你暴露出去的 ws 应用的路径，有点类似我们经常用的@RequestMapping。
 * 比如你的启动端口是8080，而这个注解的值是ws，那我们就可以通过 ws://127.0.0.1:8080/ws 来连接你的应用
 * @OnOpen 当 websocket 建立连接成功后会触发这个注解修饰的方法，注意它有一个 Session 参数
 * @OnClose 当 websocket 建立的连接断开后会触发这个注解修饰的方法，注意它有一个 Session 参数
 * @OnMessage 当客户端发送消息到服务端时，会触发这个注解修改的方法，它有一个 String 入参表明客户端传入的值
 * @OnError 当 websocket 建立连接时出现异常会触发这个注解修饰的方法，注意它有一个 Session 参数
 * 另外一点就是服务端如何发送消息给客户端，服务端发送消息必须通过上面说的 Session 类，
 * 通常是在@OnOpen 方法中，当连接成功后把 session 存入 Map 的 value，key 是与 session 对应的用户标识，
 * 当要发送的时候通过 key 获得 session 再发送，这里可以通过 session.getBasicRemote().sendText() 来对客户端发送消息。
 **/

@ServerEndpoint("/chatting/{parm}")
@Component
public class SocketServer {
    private static final Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("parm") String parm, @PathParam("username") String username, @PathParam("toUsername") String toUsername) {
        System.out.println("连接成功:" + session.getId() + " name=" + username);
//        System.out.println(username2);
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();

        System.out.println(session.getPathParameters());
        System.out.println(session.getRequestURI());
        System.out.println(session.getRequestParameterMap());
        System.out.println(session.getQueryString());

//        后面处理成多记录的模式
        username = requestParameterMap.get("username").get(0);
        toUsername = requestParameterMap.get("toUsername").get(0);
        System.out.println("用户=" + username + ":" + toUsername);
        sessionMap.put(username, session);
    }

    /**
     * 接收到消息
     *
     * @param parm
     */
    @OnMessage
    public void OnMessage(String message, Session session, @PathParam("parm") String parm, @PathParam("username") String username, @PathParam("toUsername") String toUsername) throws IOException {
        try {
//            给客户端发送
            System.out.println(username + ":" + toUsername + ":" + message);
//            获取参数不用那么费劲，直接获取session里的pathParameters
            Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
            username = requestParameterMap.get("username").get(0);
            toUsername = requestParameterMap.get("toUsername").get(0);
            System.out.println(requestParameterMap);
            System.out.println("username=" + username + "toUsername=" + requestParameterMap.get("toUsername") + " sessionId=" + session.getId() + "" + "");

            JSONObject jsonObject = new JSONObject(message);
            Map<String, Object> messageMap = jsonObject.toMap();
            System.out.println(messageMap.toString());
            System.out.println("名字=" + messageMap.get("username"));
            Session fromSession = sessionMap.get(username);
            Session toSession = sessionMap.get(toUsername);

//            toSession.getBasicRemote().sendText(message);
            sendMessage(message, fromSession, toSession);

//            sendMessageToAll(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private void sendMessage(String message, Session fromSession, Session toSession) {
        try {
            toSession.getBasicRemote().sendText(message);
        } catch (NullPointerException e) {
            if (fromSession == null) {
                throw new RuntimeException(e);
            } else {
                JSONObject jsonObject = new JSONObject(message);
                Map<String, Object> returnObjectMap = jsonObject.toMap();
                returnObjectMap.put("message","你要发送消息的目标用户不在线");
                message=returnObjectMap.toString();
                System.out.println(message);
                message = jsonObject.toString();
                System.out.println(message);
                try {
                    fromSession.getBasicRemote().sendText(message);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessageToAll(String message) {
        try {
            for (Map.Entry<String, Session> entry : sessionMap.entrySet()) {
                entry.getValue().getBasicRemote().sendText(message);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 连接关闭
     *
     * @param parm
     */
    @OnClose
    public void onClose(@PathParam("parm") String parm, @PathParam("username") String username, @PathParam("toUsername") String toUsername,
                        Session session) {
        System.out.println("连接关闭:" + session.getId());
        Map<String, List<String>> requestParameterMap = session.getRequestParameterMap();
        username = requestParameterMap.get("username").get(0);
        toUsername = requestParameterMap.get("toUsername").get(0);
        sessionMap.remove(username);
    }


}
