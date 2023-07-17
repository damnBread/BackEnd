package com.example.damnbreadback.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Message {

    private String id;

    private String content; // 채팅 내용
    private Date date; // 채팅일
    private boolean sendingUser; // 채팅 발신자 (true : user1, false : user2)
    private boolean read; // 읽음 여부
}