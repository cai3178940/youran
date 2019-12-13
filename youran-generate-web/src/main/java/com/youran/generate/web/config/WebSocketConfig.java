package com.youran.generate.web.config;

import com.youran.generate.config.GenerateAuthentication;
import com.youran.generate.constant.WebConst;
import com.youran.generate.web.context.GenerateLoginContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

/**
 * websocket配置
 *
 * @author cbb
 * @date 2019/2/21
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Value(WebConst.WS_API_PATH)
    private String wsApiPath;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // 启用内置简单broker，可订阅路径前缀为/code_gen
        // 浏览器连接websocket以后，就能订阅 /code_gen/* 主题了
        config.enableSimpleBroker("/code_gen");
        // 配置stomp协议的消息接收路径前缀
        // 浏览器连接websocket以后，就能给 /code_gen/* 发送消息
        config.setApplicationDestinationPrefixes("/code_gen");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 注册websocket的url路径
        registry.addEndpoint(wsApiPath)
            .setAllowedOrigins("*")
            .withSockJS()
            .setInterceptors(httpSessionHandshakeInterceptor());;
    }


    @Bean
    public HandshakeInterceptor httpSessionHandshakeInterceptor() {

        return new HandshakeInterceptor() {
            @Override
            public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                           WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
                // 如果集成了外部单点登录，需要在此获取用户授权信息
                GenerateAuthentication authentication = new GenerateAuthentication(GenerateLoginContext.MOCK_LOGIN_USER);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                return true;
            }
            @Override
            public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Exception exception) {
            }
        };
    }

}
