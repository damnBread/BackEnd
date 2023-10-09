package com.example.damnbreadback.controller;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ChattingSocketController {

    private static Set<Integer> userList = new HashSet<>();

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/chat/{id}")
    public void sendMessage(@Payload ChatMessageDTO messageDTO, @DestinationVariable Integer id){
        System.out.println(messageDTO);
        this.simpMessagingTemplate.convertAndSend("/queue/addChatToClient/"+id,messageDTO);
    }
//
//    @MessageMapping("/join")
//    public void joinUser(@Payload Integer userId){
//        userList.add(userId);
//        userList.forEach(user-> System.out.println(user));
//    }

}