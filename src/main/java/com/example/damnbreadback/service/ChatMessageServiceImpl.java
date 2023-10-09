package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.ChatMessageDTO;
import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;

    @Transactional
    public void save(final ChatMessage chatMessage) {
//        ChatMessage chatMessage = requestDto.toEntity();
        chatMessageRepository.save(chatMessage);
    }

//    @Transactional
//    public List<ChatMessageResponseDto> findRoom(int roomNumber) {
//
//        List<ChatMessage> chatMessage = this.chatMessageRepository.findByRoomNumber(roomNumber);
//
//        List<ChatMessageResponseDto> dtolist = new ArrayList<>();
//        for (ChatMessage chatMessage1 : chatMessage) {
//            ChatMessageResponseDto responseDto = new ChatMessageResponseDto(chatMessage1);
//            dtolist.add(responseDto);
//        }
//        return dtolist;
//    }
}