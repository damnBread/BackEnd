package com.example.damnbreadback.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserFilter {
    String location;// 지역
    String job; // 업직종
    Integer[] gender; //성별 조건
    int age;  // 연령 조건
    int career; // 경력 조건
}