package com.mike.config;

import com.mike.Handler.HandshakeInterceptorCheck;
import com.mike.Handler.WebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @Author: 23236
 * @createTime: 2022/11/29   14:59
 * @description:
 **/

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    WebSocketHandler webSocketHandler;
    @Autowired
    HandshakeInterceptorCheck handshakeInterceptorCheck;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println(webSocketHandlerRegistry);
        webSocketHandlerRegistry.addHandler(webSocketHandler, "/chat/*","/ws-chat/*")
                .addInterceptors(handshakeInterceptorCheck);
    }

}
