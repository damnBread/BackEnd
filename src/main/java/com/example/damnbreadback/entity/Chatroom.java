package com.example.damnbreadback.entity;

import com.google.firebase.database.annotations.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="chatroom")
public class Chatroom {

    @Id
    @NotNull
    @GeneratedValue
    private Long roomId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post")
    private Post post; // 공고

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user1")
    private User user1; // 사용자1

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user2")
    private User user2; // 사용자2


    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "chat")
    private Set<ChatMessage> chats; // 채팅메세지

}