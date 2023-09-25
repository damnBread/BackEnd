package com.example.damnbreadback.service;

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
    public int countByChatId(Long user1_id, Long user2_id) {
        System.out.println(chatRepository.countChatroomByUser1UserIdAndUser2UserId(user1_id, user2_id));
        return chatRepository.countChatroomByUser1UserIdAndUser2UserId(user1_id, user2_id);
    }


//    @Override
//    public List<Chatroom> getChattings() throws ExecutionException, InterruptedException {
//        return chatRoomRepository.getChattings();
//    }


    public Optional<Chatroom> startChat (Long postId,Long user1_id,Long user2_id)throws ExecutionException, InterruptedException {
        //이미 chatRoom이 만들어져있는지 -> damnid와 userid로 확인.
        //TODO 지금 user1과 user2의 경계가 이상함... 내가 시작한 채팅이든 남이 시작한 채팅이든 어쨋든 떠야되는데 1번이 user1이고 2번이 user2이면,
        // 2번으로 해서 url 설정하면 user1 != 2 니까 안나옴.
        // 채팅 API 다시 정리하기 -> 어쨌든 다 채팅으로 가는데 그걸 mypage로 경로설정하는게 맞는가?
        if (countByChatId(user1_id, user2_id) > 0) {
            //get ChatRoomInfo
            Chatroom chatRoomTemp = getChatRoom(postId, user1_id, user2_id);
            //load existing chat history
//            List<ChatRoom> chatHistory = readChatHistory(chatRoomTemp);
//            //transfer chatHistory Model to View
//            model.addAttribute("chatHistory", chatHistory);
            return Optional.ofNullable(chatRoomTemp);

        } else {
            System.out.println("count2");
            //chatRoom 생성

            Optional<Post> optionalPost = postService.getPostById(postId);
            if (optionalPost.isPresent()) {
                Post post = optionalPost.get();

                User user1 = User.toEntity(userService.getUserById(user1_id));
                User user2 = User.toEntity(userService.getUserById(user2_id));

                if (user1 == null || user2 == null) return null;

                return Optional.ofNullable(createChatRoom(post, user1, user2));
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
//        return chatRepository.getChatrooms(userId);
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
    public Chatroom createChatRoom(Post post,User user1,User user2) throws ExecutionException, InterruptedException{
//        String randomId = UUID.randomUUID().toString();

        Chatroom chatRoom = new Chatroom();
        chatRoom.setPost(post);
        chatRoom.setUser1(user1);
        chatRoom.setUser2(user2);
//        chatRoom.setRoomId(Math.random(0, 10));

        chatRepository.save(chatRoom);

        return chatRoom;
    }
}