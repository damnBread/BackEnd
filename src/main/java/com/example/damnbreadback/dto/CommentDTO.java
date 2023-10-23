package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.Comment;
import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.entity.User;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CommentDTO {
    private String content;
    private Date date;
    private Long writerId;
    private Long storyId;

    public static CommentDTO toDTO(Comment entity) {
        Long storyId = null;
        Long writerId = null;

        if(entity.getStory() != null) storyId = entity.getStory().getId();
        if(entity.getWriter() != null) writerId = entity.getWriter().getUserId();
        try {
            return CommentDTO.builder()
                    .storyId(storyId)
                    .writerId(writerId)
                    .date(entity.getDate())
                    .content(entity.getContent())
                    .build();
        } catch (Error e) {
            return null;
        }
    }
}