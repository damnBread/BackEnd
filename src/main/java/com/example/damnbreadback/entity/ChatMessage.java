package com.example.damnbreadback.entity;

import com.example.damnbreadback.dto.ChatMessageDTO;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="message")
public class ChatMessage {

    @Id
    private String chatNum;

    private String content; // 채팅 내용
    private Date date; // 채팅일
    private boolean sendingUser; // 채팅 발신자 (true : user1, false : user2)
    private boolean isRead; // 읽음 여부

    @ManyToOne
    @JoinColumn(name = "chat")
    private Chatroom room;

    public static ChatMessage toEntity(ChatMessageDTO dto, Chatroom room) {
        try {
            return ChatMessage.builder()
                    .chatNum(dto.getChatId())
                    .content(dto.getContent())
                    .date(dto.getDate())
                    .sendingUser(dto.isSendingUser())
                    .isRead(dto.isRead())
                    .room(room)
                    .build();

        } catch (Error e) {
            return null;
        }
    }
}