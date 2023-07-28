package com.example.damnbreadback.entity;

import jakarta.persistence.*;
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
@Entity
@Table(name="comment")
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String content; // 댓글 내용
    private Date date; // 댓글 게시일
    private Long writer; // 댓글 게시자

    @ManyToOne
    @JoinColumn(name="story")
    private Story story;
}