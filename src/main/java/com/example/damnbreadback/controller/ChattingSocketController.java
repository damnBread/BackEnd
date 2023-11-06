package com.example.damnbreadback.controller;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.service.ChatMessageService;
import com.example.damnbreadback.service.ChatroomService;
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
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@Controller
public class ChattingSocketController {

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

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatMessageService chatMessageService;
    @Autowired
    private ChatroomService chatroomService;

    @MessageMapping("/chat")
    public void sendMessage(Map<Object, Object> chatMessageInfo, SimpMessageHeaderAccessor accessor) throws ExecutionException, InterruptedException {
        System.out.println(accessor.getSessionId());
        System.out.println("message ::: " + chatMessageInfo);
        String message = chatMessageInfo.get("chat").toString();
        Long sender = Long.parseLong(chatMessageInfo.get("sender").toString());
        Long receiver = Long.parseLong(chatMessageInfo.get("receiver").toString());

        Chatroom chatRoom = chatroomService.startChat(sender, receiver);
        chatMessageService.saveChatMessage(chatRoom, sender, receiver, message );

        System.out.println(sender + " -> " + receiver);
        this.simpMessagingTemplate.convertAndSend("/sub/chat/" + chatMessageInfo.get("receiver").toString(), chatMessageInfo);
    }
}