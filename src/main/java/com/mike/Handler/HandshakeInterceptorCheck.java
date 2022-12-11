package com.mike.Handler;

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.Set;

/**
 * @Author: 23236
 * @createTime: 2022/12/4   20:11
 * @description:
 **/
@Component
public class HandshakeInterceptorCheck implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        System.out.println("握手开始");
        // 获得请求参数
        //ws:43.143.254.25:8081/ws?token=123&
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) request;
        HttpSession session = servletServerHttpRequest.getServletRequest().getSession(false);

        System.out.println(request.getHeaders().toSingleValueMap());
        System.out.println("intercepter request cookie="+request.getHeaders().toSingleValueMap().get("cookie"));
        System.out.println("intercepter request body="+request.getBody().toString());
//        attributes= request.getHeaders().toSingleValueMap();
        Set<Map.Entry<String, String>> entries = request.getHeaders().toSingleValueMap().entrySet();
        for (Map.Entry en:entries) {
            System.out.println("key="+en.getKey()+" value="+en.getValue());
            attributes.put(en.getKey().toString(),en.getValue());
        }


        String username = "maimai";
        String toUsername = "qiang";
//        ServerHttpRequest和HttpServletRequest还是有去别的
        HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
        Map<String, String[]> parameterMap = servletRequest.getParameterMap();
//        String callbackMethodName = servletRequest.getParameter(responseJsonp.callback());
        String[] usernames = parameterMap.get("username");
        String[] toUsernames = parameterMap.get("toUsername");
        System.out.println("beforeHandshake 函数调用结束");


//        Iterator<Map.Entry<String, String>> iterator = entries.iterator();
//        while (iterator.hasNext()){
//            Map.Entry entry = iterator.next();
//            System.out.println("key="+entry.getKey()+" value="+entry.getValue());
//        }

        if (username!=null&&toUsername!=null) {
//             放入属性域
            attributes.put("usernames", usernames);
            attributes.put("toUsernames", toUsernames);
            System.out.println("用户 " + usernames + " 握手成功！");
            return true;
        }
        System.out.println("用户登录已失效");
        return false;
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception exception) {

    }
}
