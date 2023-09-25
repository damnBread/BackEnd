package com.example.damnbreadback.config;

import com.example.damnbreadback.Handler.ChatHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

//@RequiredArgsConstructor
//@Configuration
//@EnableWebSocket
//@EnableWebSocketMessageBroker
//public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
//    private final ChatHandler chatHandler;
//
////    @Override
////    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
////
////        registry.addHandler(chatHandler, "chat").setAllowedOrigins("*");
////    }
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/sub");
//        config.setApplicationDestinationPrefixes("/pub");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws-stomp").setAllowedOrigins("*")
//                .withSockJS();
//    }
//}
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSockConfig.class);

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {

        //for subscribe prefix
        registry.enableSimpleBroker("/user");
        //for publish prefix
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        registry.addEndpoint("/broadcast")
                .withSockJS()
                .setHeartbeatTime(60_000);
    }

}