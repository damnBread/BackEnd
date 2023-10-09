package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageDTO {

    private String chatId;
    private Long chatRoomId;

    private String content; // 채팅 내용
    private Date date; // 채팅일
    private boolean sendingUser; // 채팅 발신자 (true : user_appliance, false : user_publisher)
    private boolean isRead; // 읽음 여부

    public static ChatMessageDTO toDTO(ChatMessage entity) {
        try {
            return ChatMessageDTO.builder()
                    .chatId(entity.getChatNum())
                    .chatRoomId(entity.getRoom().getRoomId())
                    .content(entity.getContent())
                    .date(entity.getDate())
                    .sendingUser(entity.isSendingUser())
                    .isRead(entity.isRead())
                    .build();
        } catch (Error e) {
            return null;
        }
    }
}
