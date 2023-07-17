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
public class Chatting {

    private String id;

    private String user1; // 사용자1
    private String user2; // 사용자2
    
    private ArrayList<Message> messages; //댓글들
}