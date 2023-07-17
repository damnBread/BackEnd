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
public class Application {

    private String id;

    private String post; // 지원 공고 id
    private String applicant; // 지원자 id
    private boolean damn; // 땜빵 여부
}