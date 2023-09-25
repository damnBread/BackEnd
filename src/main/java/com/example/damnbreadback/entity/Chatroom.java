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
    @JoinColumn(name = "user_publiser")
    private User user_publisher; // 게시자 사용자

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_appliance")
    private User user_appliance; // 지원자 사용자


    @OneToMany( fetch = FetchType.EAGER)
    @JoinColumn(name = "chat")
    private Set<ChatMessage> chats; // 채팅메세지

}