package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatroomServiceImpl implements ChatroomService {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ChatroomRepository chatroomRepository;

    public List<ChatRoomDTO> getChatRooms(Long userId){
        List<Chatroom> chatroomList = chatroomRepository.getChatrooms(userId);
        List<ChatRoomDTO> chatRoomDTOList = new ArrayList<>();

        chatroomList.forEach(c -> {
            chatRoomDTOList.add(ChatRoomDTO.toDTO(c));
        });
        return chatRoomDTOList;
    }

    @Override
    public int countByChatId(Long user1_id, Long user2_id) {
        System.out.println(chatroomRepository.countChatroomByAll( user1_id, user2_id));
        return chatroomRepository.countChatroomByAll( user1_id, user2_id);
    }


//    @Override
//    public List<Chatroom> getChattings() throws ExecutionException, InterruptedException {
//        return chatRoomRepository.getChattings();
//    }

    public Chatroom startChat(Long user1, Long user2) throws ExecutionException, InterruptedException {
        //이미 chatRoom이 만들어져있는지 -> damnid와 userid로 확인.
        if (countByChatId(user1, user2) > 0) {
            Chatroom chatRoom = chatroomRepository.getChatroomByAll(user1, user2);

            return chatRoom;
        } else {
            //chatRoom 생성
            if (user1 == null || user2 == null) return null;

            UserDTO user1_DTO = userService.getUserById(user1);
            UserDTO user2_DTO = userService.getUserById(user2);

            if(user1_DTO == null || user2_DTO == null) return null;

            User user1_obj = User.toEntity(user1_DTO);
            User user2_obj = User.toEntity(user2_DTO);

            return createChatRoom(user1_obj, user2_obj);
        }

    }

    @Override
    public Chatroom getChatRoom(Long postId, Long user1_id, Long user2_id) throws ExecutionException, InterruptedException {
        return null;
//        return ChatroomRepository.getChatroom()
    }

    @Override
    public List<ChatMessageDTO> getChatMessages(Long roomId) throws ExecutionException, InterruptedException {
        Chatroom chatroom = chatroomRepository.getChatroomByRoomId(roomId);
        System.out.println(chatroom);
        if (chatroom == null) {
            return null;
        }

        //채팅 메세지 불러오기 ...
        List<ChatMessage> chatMessageList = new ArrayList<>(chatroom.getChats());
        List<ChatMessageDTO> chatMessageDTOList = new ArrayList<>();
        chatMessageList.forEach(m -> {
            chatMessageDTOList.add(ChatMessageDTO.toDTO(m));
        });
        return chatMessageDTOList;
//        return null;
    }

    @Override
    public Chatroom createChatRoom(User user_publisher, User user_appliance) throws ExecutionException, InterruptedException {
//        String randomId = UUID.randomUUID().toString();

        Chatroom chatRoom = new Chatroom();
        chatRoom.setUser1(user_publisher);
        chatRoom.setUser2(user_appliance);
//        chatRoom.setRoomId(Math.random(0, 10));

        chatroomRepository.save(chatRoom);

        return chatRoom;
    }


}