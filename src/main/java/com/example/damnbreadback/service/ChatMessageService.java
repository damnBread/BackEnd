package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface ChatMessageService {

    void saveChatMessage(Map<Object, Object> chatMessageInfo);
}