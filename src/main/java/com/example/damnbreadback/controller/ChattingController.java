package com.example.damnbreadback.controller;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.service.ChatroomService;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        Long userId = userService.findUserIdById(authentication.getName());

        if(userId == null) return ResponseEntity.status(405).body("잘못된 접근입니다.");
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


//    // 지원자(appliance) -> 게시자(publisher) 채팅 (damnlist ; 구직게시판)
//    @RequestMapping(value = "/damnlist/chat", method = RequestMethod.POST)
//    public ResponseEntity<Object> startChatDamnList(Authentication authentication, @RequestBody Map<String, Long> chattingUsers) throws ExecutionException, InterruptedException {
//        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
//        System.out.println(authentication.getName());
//
//        ChatRoomDTO chatroom = chatroomService.startChat(chattingUsers.get("user_appliance"), chattingUsers.get("user_publisher"));
//        if(chatroom != null)  return ResponseEntity.ok().body(chatroom);
//        else return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
//    }
//
//    // 게시자(publisher) -> 지원자(appliance) 채팅 (damnrank ; 인재정보)
//    @RequestMapping(value = "/damnrank/detail/chat", method = RequestMethod.POST)
//    public ResponseEntity<Object> startChatDamnRank(Authentication authentication, @RequestBody Map<String, Long> chattingUsers) throws ExecutionException, InterruptedException {
//        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
//        System.out.println(authentication.getName());
//
//        ChatRoomDTO chatroom = chatroomService.startChat(chattingUsers.get("user_appliance"), chattingUsers.get("user_publisher"));
//        if(chatroom != null)  return ResponseEntity.ok().body(chatroom);
//        else return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
//
//    }

    //TODO : 채팅방 지우는 것도 해야함. ( 채팅방 나가기, 채팅방 없애기 )

}
