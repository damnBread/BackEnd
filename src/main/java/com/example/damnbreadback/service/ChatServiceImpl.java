package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    @Autowired
    private final ChatRepository chatRepository;

//    @Override
//    public List<Chatroom> getChattings() throws ExecutionException, InterruptedException {
//        return chatRoomRepository.getChattings();
//    }


    @Override
    public List<Chatroom> getChatRoom(String userId) throws ExecutionException, InterruptedException {
        return chatRepository.getChatrooms(userId);
    }

    @Override
    public List<ChatMessage> getChatMessages(String roomId) throws ExecutionException, InterruptedException {
        Chatroom chatroom = chatRepository.getChatroomByRoomId(roomId);
        System.out.println(chatroom);
        if(chatroom == null) {
            return null;
        }

        //채팅 메세지 불러오기 ...
        System.out.println(chatroom.getChats());
//        return (List<ChatMessage>) chatroom.getChats();
        return null;
    }

    @Override
    public Chatroom createChatRoom(String name) throws ExecutionException, InterruptedException{
        String randomId = UUID.randomUUID().toString();

        Chatroom chatRoom = new Chatroom();
        chatRoom.setRoomId(randomId);

        chatRepository.save(chatRoom);

        return chatRoom;
    }
}