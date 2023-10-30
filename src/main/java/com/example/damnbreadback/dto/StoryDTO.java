package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.Comment;
import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.entity.User;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class StoryDTO {
    private Long id;

    private String title; // 썰 제목
    private String content; // 썰 내용
    private Long writer; // 썰 게시자

    private int viewCount; // 썰 조회수

    public static StoryDTO toDTO(Story entity) {
        try {
            return StoryDTO.builder()
                    .id(entity.getId())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .writer(entity.getWriter())
                    .viewCount(entity.getViewCount())
                    .build();
        } catch (Error e) {
            return null;
        }
    }
}
