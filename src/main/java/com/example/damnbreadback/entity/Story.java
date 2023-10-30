package com.example.damnbreadback.entity;

import com.example.damnbreadback.dto.StoryDTO;
import com.example.damnbreadback.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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

    public static Story toEntity(StoryDTO dto){
        return Story.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .content(dto.getContent())
                .writer(dto.getWriter())
                .viewCount(dto.getViewCount())
                .comments(dto.getComments())
                .build();
    }

}