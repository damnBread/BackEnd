package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name="history")
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

    @ColumnDefault("0")
    private int statusCode;

//    private String company; // 근무지
//    private Date startDate; // 근무 시작 일시
//    private Date endDate; // 근무 종료 일시

}