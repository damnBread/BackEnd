package com.example.damnbreadback.entity;

import com.example.damnbreadback.dto.ChatRoomDTO;
import com.example.damnbreadback.dto.UserDTO;
import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name="chatroom")
public class Chatroom {

    @Id
    @NotNull
    @GeneratedValue
    private Long roomId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user1")
    private User user1; // 게시자 사용자

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user2")
    private User user2; // 지원자 사용자


    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "chatroom")
    private Set<ChatMessage> chats; // 채팅메세지

}