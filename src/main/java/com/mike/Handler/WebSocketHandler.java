package com.mike.Handler;

import com.google.gson.Gson;
import com.mike.bean.User;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 23236
 * @createTime: 2022/12/3   16:15
 * @description:
 **/
@Component
public class WebSocketHandler extends TextWebSocketHandler {    //BinaryWebSocketHandler 就两种

    @Autowired
    User user;
    List<WebSocketSession> webSocketSessions = Collections.synchronizedList(new ArrayList<>());
    private static final Map<String, List<WebSocketSession>> sessionMap = new ConcurrentHashMap<>();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        webSocketSessions.add(session);

        System.out.println("afterConnectionEstablished进来了");
        Map<String, Object> attributes1 = session.getAttributes();
        System.out.println(attributes1);
        System.out.println(session.getUri());

        String[] usernames = (String[]) (session.getAttributes().get("usernames"));
        String[] toUsernames = (String[]) (session.getAttributes().get("toUsernames"));
        System.out.println("sockethandler里=" + usernames.toString() + toUsernames.toString());
        String username = "";
        List<WebSocketSession> listSession=Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < usernames.length; i++) {
            username=usernames[i];
            if (sessionMap.get(username) == null) {
                listSession.add(session);
                sessionMap.put(username,listSession);
            } else {
                listSession = sessionMap.get(username);
                listSession.add(session);
                sessionMap.replace(username, listSession);
            }
        }


//        sessionMap.put(session.getAttributes().get("username").toString(),webSocketSessions);
        System.out.println("键连接成功:" + session);
        Map<String, Object> attributes = session.getAttributes();
        System.out.println(attributes);
//        System.out.println("idinfo:"+session.getId()+"\nname:"+name);


//        socket心跳还没了解
//        ServerSocket serverSocket = new ServerSocket(83);
//        try {
//            while(true) {
//                Socket socket = null;
//                socket = serverSocket.accept();
//                //这边获得socket连接后开启一个线程监听处理数据
//                SocketServerThread socketServerThread = new SocketServerThread(socket);
//                new Thread(socketServerThread).start();
//            }
//        } catch(Exception e) {
////            log.error("Socket accept failed. Exception:{}", e.getMessage());
//        } finally {
//            if(serverSocket != null) {
//                serverSocket.close();
//            }
//        }
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);

        System.out.println("wsh:handlemessage进来了:" + session + "\n" + message);
        String[] usernames = (String[]) (session.getAttributes().get("usernames"));
        String[] toUsernames = (String[]) (session.getAttributes().get("toUsernames"));

        String payload = message.getPayload().toString();

        System.out.println("获得参数=" + usernames.toString() + ":" + toUsernames.toString());
        System.out.println(payload);
//        数据转换1
        GsonJsonParser gsonJsonParser = new GsonJsonParser();
        Map<String, Object> stringObjectMap = gsonJsonParser.parseMap(payload.toString());
        System.out.println("gson:" + stringObjectMap);

//        数据转换2
        Gson gson = new Gson();
        String s = gson.toJson(stringObjectMap);
        System.out.println("tojson:" + s);
        System.out.println("payload==s:" + payload.equals(s));
//        数据转换3
        JSONObject jsonObject = new JSONObject(payload);
        System.out.println(jsonObject.toMap().get("username") + ":" + jsonObject.toMap().get("message"));

        Map<String, String> map = new HashMap<>();

        sendMessage(usernames,toUsernames, message);


    }

    private void sendMessage(String[] origin,String[] targets, WebSocketMessage<?> message) {
        try {
            System.out.println("叫【" + targets[0] + "】的所有人，\n将会收到={" + message + "}的消息。");
            String toUser="";
            List<WebSocketSession> listSession;
            for (int i = 0; i < targets.length; i++) {
                toUser=targets[i];
                listSession=sessionMap.get(toUser);
                System.out.println("发送="+toUser);
                System.out.println("链接数="+listSession.size());
                for (WebSocketSession webSocketSession : listSession) {
                    webSocketSession.sendMessage(message);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //    群发消息
    private void pocketTransmission(Map<String, List<WebSocketSession>> stringListMap, WebSocketMessage<?> message) {
        try {
            for (Map.Entry<String, List<WebSocketSession>> entry : stringListMap.entrySet()) {
                String username = entry.getKey();
                System.out.println("给[" + username + "]姓名的人发送消息！");
                List<WebSocketSession> sessionList = entry.getValue();
                for (WebSocketSession webSocketSession : sessionList) {
                    webSocketSession.sendMessage(message);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        System.out.println("handleTextMessage进来了就要干事了");
//        for (WebSocketSession webSocketSession : webSocketSessions) {
//            if (webSocketSession.getId() != session.getId()) {
//                webSocketSession.sendMessage(message);
//            }
//        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        String[] usernames = (String[]) (session.getAttributes().get("usernames"));
        String[] toUsernames = (String[]) (session.getAttributes().get("toUsernames"));
        String user="";
        List<WebSocketSession> listSession=Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < usernames.length; i++) {
            user=usernames[i];
            listSession=sessionMap.get(user);
            if (listSession == null) {
                continue;
            }
            listSession.remove(session);
            if (listSession.size() == 0) {
                sessionMap.remove(user);
            }
            sessionMap.replace(user,listSession);
            System.out.println("删除了"+user+"的session还剩链接数="+listSession.size());
        }
    }

    @Override
    protected void handlePongMessage(WebSocketSession session, PongMessage message) throws Exception {
        super.handlePongMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }


    @Override
    public boolean supportsPartialMessages() {
        return super.supportsPartialMessages();
    }


}


class SocketServerThread implements Runnable {

    private Socket socket;

    public SocketServerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = socket.getInputStream();
            out = socket.getOutputStream();
            Integer sourcePort = socket.getPort();
            int maxLen = 2048;
            byte[] contextBytes = new byte[maxLen];
            int realLen;
            StringBuffer message = new StringBuffer();
            BIORead:
            while (true) {
                try {
                    while ((realLen = in.read(contextBytes, 0, maxLen)) != -1) {
                        message.append(new String(contextBytes, 0, realLen));
                        /*
                         * 我们假设读取到“over”关键字，
                         * 表示客户端的所有信息在经过若干次传送后，完成
                         * */
                        if (message.indexOf("over") != -1) {
                            break BIORead;
                        }
                    }

                    //下面打印信息
//                    log.info("服务器(收到来自于端口：" + sourcePort + "的信息：" + message);
                    //下面开始发送信息
                    out.write("回发响应信息！".getBytes());
                    //关闭
                    out.close();
                    in.close();
                    this.socket.close();
                } catch (Exception e) {

                    throw new RuntimeException(e);
                } finally {
                    new Throwable();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
