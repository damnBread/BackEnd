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
public class Comment {

    private String id;

    private String content; // 댓글 내용
    private Date date; // 댓글 게시일
    private String writer; // 댓글 게시자
}