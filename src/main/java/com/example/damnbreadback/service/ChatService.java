package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChatService {

    // /chatlist에서 채팅방 목록 불러오기
    List<Chatroom> getChatRoom(String userId) throws ExecutionException, InterruptedException;

    List<ChatMessage> getChatMessages(String roomId) throws ExecutionException, InterruptedException;
    Chatroom createChatRoom(String name) throws ExecutionException, InterruptedException;
}