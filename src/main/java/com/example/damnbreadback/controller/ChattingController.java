package com.example.damnbreadback.controller;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.service.ChatroomService;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ChattingController {

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ChatroomService chatroomService;

    //채팅방 목록 가져오기.
    @GetMapping("/chatlist")
    public ResponseEntity<Object> getChatList(Authentication authentication) throws ExecutionException, InterruptedException {
        Long userId = userService.findUserIdById(authentication.getName());
        List<ChatRoomDTO> rooms = chatroomService.getChatRooms(userId);

        if(rooms == null) return ResponseEntity.badRequest().body("잘못된 접근입니다.");
        else return ResponseEntity.ok().body(rooms);
    }
    
    // 채팅방 입장 -> 세부 : 메시지 기록 가져오기.
    @GetMapping("/chatlist/enter")
    public ResponseEntity<Object> getRoomDetail(@RequestParam Long roomId) throws ExecutionException, InterruptedException {

        List<ChatMessageDTO> chatMessageList = chatroomService.getChatMessages(roomId);
        return ResponseEntity.ok().body(chatMessageList);
    }


    // 지원자 -> 게시자 채팅 (damnlist ; 구직게시판)
    @RequestMapping(value = "/damnlist/{postNum}/chat", method = RequestMethod.POST)
    public ResponseEntity<Object> startChat1(Authentication authentication, @PathVariable Long postNum) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        Long user_appliance = userService.findUserIdById(authentication.getName());
        Long user_publisher = postService.getPostById(postNum).get().getPublisher();//postNum을 작성한 게시자 찾기.

        ChatRoomDTO chatroom = chatroomService.startChat(postNum,user_appliance, user_publisher);
        if(chatroom != null)  return ResponseEntity.ok().body(chatroom);
        else return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
    }

//  게시자 -> 지원자 채팅 (damnrank ; 인재정보)
//    @RequestMapping(value = "/damnrank/detail/{userid}/chat", method = RequestMethod.POST)
//    public ResponseEntity<Object> startChat2(Authentication authentication, @PathVariable Long userid) throws ExecutionException, InterruptedException {
//        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
//        System.out.println(authentication.getName());
//
//        Long user_appliance = userService.findUserIdById(authentication.getName());
//        Long user_publisher = postService.getPostById(postNum).get().getPublisher();//postNum을 작성한 게시자 찾기.
//
//        Optional<Chatroom> chatroom = chatroomService.startChat(postNum,user_appliance, user_publisher);
//        if(chatroom.isPresent())  return ResponseEntity.ok().body(chatroom.get());
//        else return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
//
//    }

}
