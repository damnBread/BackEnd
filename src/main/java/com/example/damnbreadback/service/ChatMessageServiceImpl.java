package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.dto.ChatRoomDTO;
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

    public void saveChatMessage(Chatroom chatroom, Long sender_id, Long receiver_id, String message) {
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setContent(message);
        chatMessage.setDate(new Date());
        chatMessage.setRead(false);

        User receiver = userRepository.findUserByUserId(receiver_id);
        User sender = userRepository.findUserByUserId(sender_id);
        chatMessage.setReceiver(receiver);
        chatMessage.setSender(sender);
        chatMessage.setRoom(chatroom);

        chatMessageRepository.save(chatMessage);
    }
}