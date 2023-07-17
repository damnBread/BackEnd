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
public class Story {

    private String id;

    private String title; // 썰 제목
    private String content; // 썰 내용
    private Date date; // 썰 게시일
    private String writer; // 썰 게시자
    private int viewCount; // 썰 조회수
    
    private ArrayList<Comment> comments; //댓글들
}