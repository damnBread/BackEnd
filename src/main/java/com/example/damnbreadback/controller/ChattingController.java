package com.example.damnbreadback.controller;

import com.example.damnbreadback.dto.ChatMessage;
import com.example.damnbreadback.dto.ChatRoom;
import com.example.damnbreadback.repository.ChatRoomRepository;
import com.example.damnbreadback.service.ChatService;
import com.example.damnbreadback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chatlist")
public class ChattingController {

//    @Autowired
//    ChatService chatService;
//
//    @PostMapping(value ="/new")
//    public ResponseEntity<Object> createNewChat(@RequestBody String roomName){
//        return ResponseEntity.ok().body(chatService.createRoom(roomName));
//    }
//
//    @GetMapping
//    public List<ChatRoom> findAllRoom() {
//        System.out.println(chatService.findAllRoom());
//        return chatService.findAllRoom();
//    }

        private final ChatRoomRepository chatRoomRepository;

        // 모든 채팅방 목록 반환
        @GetMapping("")
        @ResponseBody
        public ResponseEntity<Object> getChatList() {
            return ResponseEntity.ok().body(chatRoomRepository.findAllRoom());
        }

        // 채팅방 생성
        @PostMapping("/newchat")
        @ResponseBody
        public ResponseEntity<Object> createRoom(@RequestParam String name) {
            return ResponseEntity.ok().body(chatRoomRepository.createChatRoom(name));
        }

        // 채팅방 입장 화면
        @GetMapping("/room/{roomId}")
        public ResponseEntity<Object> roomDetail(@PathVariable String roomId) {
            return ResponseEntity.ok().body(chatRoomRepository.findRoomById(roomId));
        }

//        // 특정 채팅방 조회
//        @GetMapping("/room/{roomId}")
//        @ResponseBody
//        public ChatRoom roomInfo(@PathVariable String roomId) {
//            return chatRoomRepository.findRoomById(roomId);
//        }
    }


