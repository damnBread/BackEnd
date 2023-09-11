package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
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
}