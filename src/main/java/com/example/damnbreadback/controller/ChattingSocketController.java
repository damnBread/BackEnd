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
import org.springframework.web.util.HtmlUtils;

import java.util.HashSet;
import java.util.Set;

@Controller
public class ChattingSocketController {

    private static Set<Integer> userList = new HashSet<>();

    //    @Autowired
//    private SimpMessagingTemplate simpMessagingTemplate;
//
//    @MessageMapping("/chat/{id}")
//    public void sendMessage(@Payload ChatMessageDTO messageDTO, @DestinationVariable Integer id){
//        System.out.println(messageDTO);
//        this.simpMessagingTemplate.convertAndSend("/queue/addChatToClient/"+id,messageDTO);
//    }
//
//    @MessageMapping("hello")
//    @SendTo("/topic/greeting")
//    public void broadcasting(@DestinationVariable(value = "roomNo") final String chatRoomNo) {
//
//        System.out.println("{roomNo : {}, request : {}}" + chatRoomNo);
//
//        return;
//    }
//

    private SimpMessagingTemplate simpMessagingTemplate;
    @MessageMapping("/chat")
    public void sendMessage(ChatMessageDTO messageDTO, SimpMessageHeaderAccessor accessor) {
        simpMessagingTemplate.convertAndSend("/sub/chat/" + messageDTO.getChatId(), messageDTO);
    }
}