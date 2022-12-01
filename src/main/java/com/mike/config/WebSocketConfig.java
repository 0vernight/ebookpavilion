package com.mike.config;

import com.mike.controller.SocketController;
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
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        System.out.println(webSocketHandlerRegistry);
        webSocketHandlerRegistry.addHandler(new SocketController(), "/chat/*","name");
    }
}
