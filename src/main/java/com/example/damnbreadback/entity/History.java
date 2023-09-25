package com.example.damnbreadback.entity;

import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue
    private Long historyId;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user")
    private User user;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "post")
    private Post post;

    public static History toEntity(HistoryDto dto,  User user, Post post){
        return History.builder()
                .historyId(dto.getId())
                .post(post)
                .user(user)
                .build();

    }

//    private String company; // 근무지
//    private Date startDate; // 근무 시작 일시
//    private Date endDate; // 근무 종료 일시

}