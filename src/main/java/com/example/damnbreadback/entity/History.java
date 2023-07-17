package com.example.damnbreadback.entity;

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
public class History {

    private String id;
    
    private String company; // 근무지
    private Date startDate; // 근무 시작 일시
    private Date endDate; // 근무 종료 일시
}