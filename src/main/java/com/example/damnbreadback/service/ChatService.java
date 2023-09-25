package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface ChatService {
    Optional<Chatroom> startChat (Long postId, Long user1_id, Long user2_id) throws ExecutionException, InterruptedException;
    // /chatlist에서 채팅방 목록 불러오기
    Chatroom getChatRoom(Long postId,Long user1_id,Long user2_id) throws ExecutionException, InterruptedException;

    List<ChatMessage> getChatMessages(Long roomId) throws ExecutionException, InterruptedException;
    Chatroom createChatRoom(Post post,User user1,User user2) throws ExecutionException, InterruptedException;

    int countByChatId(Long user1_id, Long user2_id);
}