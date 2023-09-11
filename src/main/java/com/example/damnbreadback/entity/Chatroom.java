package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="chatroom")
public class Chatroom {

    @Id
    private String roomId;

    private Long user1; // 사용자1
    private Long user2; // 사용자2


    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "chat")
    private Set<ChatMessage> chats; // 채팅메세지

}