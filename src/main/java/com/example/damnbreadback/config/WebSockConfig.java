package com.example.damnbreadback.config;

import com.example.damnbreadback.Handler.ChatHandler;
import com.example.damnbreadback.Handler.CustomHandshakeInterceptor;
import com.example.damnbreadback.Handler.SocketHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSockConfig implements WebSocketConfigurer {

    @Autowired
    SocketHandler socketHandler;

    // 요청은 핸들러로 라우트 되고
    // beforeHandshake메소드에서 헤더 중 필요한 값을 가져와 true값 반환하면 Upgrade 헤더와 함께 101 Switching Protocols 상태 코드를 포함한 응답 반환
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        try {
            registry.addHandler(socketHandler, "/wss/chat")
                    .addInterceptors(new HttpSessionHandshakeInterceptor(), new CustomHandshakeInterceptor())
                    .setAllowedOrigins("http://localhost:8080");
        } catch (Error e) {
            System.out.println("error 500 : " + e);
        }
    }
    //만약 CORS때문에 origin에서 403에러가 뜬다면
    //String[] origins = {"https://www.url1.com", "https://m.url2.com", "https://url3.com"}; 이렇게 여러개의 origin들을 setAllowedOrigins에 대입해도 됨

    @Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxBinaryMessageBufferSize(500000);
        container.setMaxTextMessageBufferSize(500000);
        return container;
    }


}