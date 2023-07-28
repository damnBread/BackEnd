package com.example.damnbreadback.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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

    private Long userId;
    
    private String company; // 근무지
    private Date startDate; // 근무 시작 일시
    private Date endDate; // 근무 종료 일시

}