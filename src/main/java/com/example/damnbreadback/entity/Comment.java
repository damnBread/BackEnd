package com.example.damnbreadback.entity;

import com.example.damnbreadback.dto.CommentDTO;
import com.example.damnbreadback.repository.StoryRepository;
import com.example.damnbreadback.repository.UserRepository;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;


@AllArgsConstructor
@Builder
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

    @ManyToOne
    @JoinColumn(name="writer")
    private User writer; // 댓글 게시자

    @ManyToOne
    @JoinColumn(name="story")
    private Story story;

    public static Comment toEntity(CommentDTO dto, Story story, User writer) {
        try {
            return Comment.builder()
                    .content(dto.getContent())
                    .date(dto.getDate())
                    .writer(writer)
                    .story(story)
                    .build();
        } catch (Error e) {
            return null;
        }
    }
}