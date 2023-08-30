package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.springframework.web.socket.WebSocketSession;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="chatroom")
public class Chatroom {

    @Id
    @GeneratedValue
    private String roomId;

    private Long user1; // 사용자1
    private Long user2; // 사용자2


    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "chat")
    private Set<Message> chats; // 채팅메세지

}