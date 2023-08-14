package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.Comment;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class StoryDTO {
    private Long id;

    private String title; // 썰 제목
    private String content; // 썰 내용
    private Long writer; // 썰 게시자

    private int viewCount; // 썰 조회수

    private Set<Comment> comments; //댓글들
}
