package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.ChatMessageRepository;
import com.example.damnbreadback.repository.ChatroomRepository;
import com.example.damnbreadback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ChatroomRepository chatroomRepository;

    public void saveChatMessage(Map<Object, Object> chatMessageInfo) {
        ChatMessage chatMessage = new ChatMessage();
//        chatMessage.setContent(chatMessageInfo.get("chat").toString());
//        chatMessage.setDate(new Date());
//        chatMessage.setRead(false);
//
//        User receiver = userRepository.findUserByUserId(Long.parseLong(chatMessageInfo.get("receiver").toString()));
//        User sender = userRepository.findUserByUserId(Long.parseLong(chatMessageInfo.get("sender").toString()));
//        chatMessage.setReceiver(receiver);
//        chatMessage.setSender(sender);
//
//        Chatroom chatroom = chatroomRepository.getChatroomByAll(receiver.getUserId(), receiver.getUserId());
//        chatMessage.setRoom(chatroom);

//        chatMessageRepository.save(chatMessage);
    }

//    @Transactional
//    public List<ChatMessageResponseDto> findRoom(int roomNumber) {
//
//        List<ChatMessage> chatMessage = this.chatMessageRepository.findByRoomNumber(roomNumber);
//
//        List<ChatMessageResponseDto> dtolist = new ArrayList<>();
//        for (ChatMessage chatMessage1 : chatMessage) {
//            ChatMessageResponseDto responseDto = new ChatMessageResponseDto(chatMessage1);
//            dtolist.add(responseDto);
//        }
//        return dtolist;
//    }
}