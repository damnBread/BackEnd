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

    // 마이페이지 -> 내가 의뢰한 땜빵 - 지원자 채팅
    @RequestMapping(value = "chat/{damnid}/{userid}", method = RequestMethod.POST)
    public ResponseEntity<Object> startChat(Authentication authentication, @PathVariable Long damnid, @PathVariable Long userid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        Long user1_id = userService.findUserIdById(authentication.getName());
        Long user2_id = userid;

        Optional<Chatroom> chatroom = chatService.startChat(damnid,user1_id, user2_id);
        if(chatroom.isPresent())  return ResponseEntity.ok().body(chatroom.get());
        else return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");


//        List<UserDTO> userList = historyService.getUserByHistory(damnid);
//
//        if(userList == null) return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
//        return ResponseEntity.ok().body(userList);

//        return ResponseEntity.ok().body("");
    }

}
