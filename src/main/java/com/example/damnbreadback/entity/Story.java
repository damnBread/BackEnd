package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="story")
public class Story extends BaseTimeEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String title; // 썰 제목
    private String content; // 썰 내용
    private Long writer; // 썰 게시자

    @ColumnDefault("0")
    private int viewCount; // 썰 조회수

    @OneToMany
    @JoinColumn(name="story")
    private Set<Comment> comments; //댓글들

}