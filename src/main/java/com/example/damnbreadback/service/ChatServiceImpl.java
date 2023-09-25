package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ChatRepository chatRepository;

    @Override
    public int countByChatId(Long postId, Long user1_id, Long user2_id) {
        System.out.println(chatRepository.countChatroomByAll(postId, user1_id, user2_id));
        return chatRepository.countChatroomByAll(postId, user1_id, user2_id);
    }


//    @Override
//    public List<Chatroom> getChattings() throws ExecutionException, InterruptedException {
//        return chatRoomRepository.getChattings();
//    }


    public Optional<Chatroom> startChat (Long postId,Long user_publisher,Long user_appliance)throws ExecutionException, InterruptedException {
        //이미 chatRoom이 만들어져있는지 -> damnid와 userid로 확인.
        if (countByChatId(postId, user_publisher, user_appliance) > 0) {
            //get ChatRoomInfo
//            Chatroom chatRoom = getChatRoom(postId, user_publisher, user_appliance);
            Chatroom chatRoom = chatRepository.getChatroomByAll(postId, user_publisher, user_appliance);

            return Optional.ofNullable(chatRoom);

        } else {
            //chatRoom 생성
            Optional<Post> optionalPost = postService.getPostById(postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();

                if (user_appliance == null || user_publisher == null) return null;


                User user_publisher_obj = User.toEntity(userService.getUserById(user_publisher));
                User user_appliance_obj = User.toEntity(userService.getUserById(user_appliance));

                return Optional.ofNullable(createChatRoom(post, user_publisher_obj, user_appliance_obj));
            } else {
                return null;
            }


//            //text file 생성
//            chatService.createFile(chatRoom.getPr_id(), chatService.getId(chatRoom.getPr_id(), chatRoom.getBuyerId()));
        }

    }

    @Override
    public Chatroom getChatRoom(Long postId,Long user1_id,Long user2_id) throws ExecutionException, InterruptedException {
        return null;
//        return ChatRepository.getChatroom()
    }

    @Override
    public List<ChatMessage> getChatMessages(Long roomId) throws ExecutionException, InterruptedException {
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
    public Chatroom createChatRoom(Post post,User user_publisher,User user_appliance) throws ExecutionException, InterruptedException{
//        String randomId = UUID.randomUUID().toString();

        Chatroom chatRoom = new Chatroom();
        chatRoom.setPost(post);
        chatRoom.setUser_publisher(user_publisher);
        chatRoom.setUser_appliance(user_appliance);
//        chatRoom.setRoomId(Math.random(0, 10));

        chatRepository.save(chatRoom);

        return chatRoom;
    }
}