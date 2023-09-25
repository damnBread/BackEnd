package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatlist")
public class ChatController {

    @Autowired
    ChatService chatService;
//
//    @PostMapping(value ="/new")
//    public ResponseEntity<Object> createNewChat(@RequestBody String roomName){
//        return ResponseEntity.ok().body(chatService.createRoom(roomName));
//    }
//
//    @GetMapping
//    public List<ChatRoomDTO> findAllRoom() {
//        System.out.println(chatService.findAllRoom());
//        return chatService.findAllRoom();
//    }

        // 모든 채팅방 목록 반환
        @GetMapping("")
        public ResponseEntity<Object> getChatList(@RequestParam Long userId) throws ExecutionException, InterruptedException {
            System.out.println(userId);
//            List<Chatroom> rooms = chatService.getChatRoom(userId);
//            return ResponseEntity.ok().body(rooms);
            return null;
        }

        // 채팅방 생성
        @PostMapping("/newchat")
        @ResponseBody
        public ResponseEntity<Object> createRoom(@RequestBody String name) throws ExecutionException, InterruptedException {
            return null;
//            return ResponseEntity.ok().body(chatService.createChatRoom(name));
        }

        // 채팅방 입장 화면
        @GetMapping("/enter")
        public ResponseEntity<Object> roomDetail(@RequestParam Long roomId) throws ExecutionException, InterruptedException {
            chatService.getChatMessages(roomId);
            return ResponseEntity.ok().body("ok");
        }

//        // 특정 채팅방 조회
//        @GetMapping("/room/{roomId}")
//        @ResponseBody
//        public ChatRoomDTO roomInfo(@PathVariable String roomId) {
//            return chatRoomRepository.findRoomById(roomId);
//        }
    }


