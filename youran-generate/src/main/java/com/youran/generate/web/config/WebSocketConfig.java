package com.youran.generate.web.config;

import com.youran.generate.constant.GenerateConst;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * <p>Title: websocket配置</p>
 * <p>Description: </p>
 * @author cbb
 * @date 2019/2/21
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value(GenerateConst.WS_API_PATH)
    private String wsApiPath;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用内置简单broker，可订阅路径前缀为/topic
        // 浏览器连接websocket以后，就能订阅 /topic/* 主题了
        config.enableSimpleBroker("/code_gen");
        // 配置stomp协议的消息接收路径前缀
        // 浏览器连接websocket以后，就能给 /app/* 发送消息
        config.setApplicationDestinationPrefixes("/code_gen");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册websocket的url路径
        registry.addEndpoint(wsApiPath).setAllowedOrigins("*").withSockJS();
    }

}
