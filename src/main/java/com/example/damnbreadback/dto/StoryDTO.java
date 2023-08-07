package com.example.damnbreadback.dto;

import lombok.*;

@NoArgsConstructor
@Getter
@Setter
public class StoryDTO {
    private String title; // 썰 제목
    private String content; // 썰 내용
    private String writerId; // 썰 게시자
}
