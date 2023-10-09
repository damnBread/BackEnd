package com.example.damnbreadback.Handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;

// import 생략....

@Slf4j
@Component
public class ChatHandler extends TextWebSocketHandler implements WebSocketConfigurer {

    private static List<WebSocketSession> list = new ArrayList<WebSocketSession>();

    // 사용자가 접속 했을 때 호출되는 메소드
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println(" ### 사용자 접속 ");
        list.add(session);
    }

    // 사용자가 메세지를 보냈을 때 호출되는 메소드
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // 전송된 메세지를 List의 모든 세션에 전송
        String msg = message.getPayload();
        for(WebSocketSession s : list) {
            s.sendMessage(new TextMessage(session.getAcceptedProtocol() + msg));
        }
    }

    // 사용자 접속 해제했을 때 호출되는 메소드
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println(" ### 사용자 접속 해제");
        list.remove(session);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this, "/chat-ws").setAllowedOrigins("*");
    }


}