package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface ChatroomService {

    List<ChatRoomDTO> getChatRooms(Long userId);
    ChatRoomDTO startChat (Long postId, Long user1_id, Long user2_id) throws ExecutionException, InterruptedException;
    // /chatlist에서 채팅방 목록 불러오기
    Chatroom getChatRoom(Long postId,Long user1_id,Long user2_id) throws ExecutionException, InterruptedException;

    List<ChatMessageDTO> getChatMessages(Long roomId) throws ExecutionException, InterruptedException;
    Chatroom createChatRoom(Post post,User user1,User user2) throws ExecutionException, InterruptedException;

    int countByChatId(Long postId, Long user1_id, Long user2_id);
}