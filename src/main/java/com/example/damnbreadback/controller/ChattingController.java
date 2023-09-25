package com.example.damnbreadback.controller;

import com.example.damnbreadback.config.JwtFilter;
import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.ChatService;
import com.example.damnbreadback.service.HistoryService;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class ChattingController {
    private Long expiredMs = 1000 * 60 * 60l;

    @Autowired
    UserService userService;

    @Autowired
    PostService postService;

    @Autowired
    ChatService chatService;

    // 지원자 -> 게시자 채팅 (damnlist ; 구직게시판)
    @RequestMapping(value = "/damnlist/{postNum}/chat", method = RequestMethod.POST)
    public ResponseEntity<Object> startChat1(Authentication authentication, @PathVariable Long postNum) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        Long user_appliance = userService.findUserIdById(authentication.getName());
        Long user_publisher = postService.getPostById(postNum).get().getPublisher();//postNum을 작성한 게시자 찾기.

        Optional<Chatroom> chatroom = chatService.startChat(postNum,user_appliance, user_publisher);
        if(chatroom.isPresent())  return ResponseEntity.ok().body(chatroom.get());
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
//        Optional<Chatroom> chatroom = chatService.startChat(postNum,user_appliance, user_publisher);
//        if(chatroom.isPresent())  return ResponseEntity.ok().body(chatroom.get());
//        else return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
//
//    }

}
