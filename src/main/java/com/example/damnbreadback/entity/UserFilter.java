package com.example.damnbreadback.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class UserFilter {
    String location;// 지역
    String job; // 업직종
    ArrayList<Integer> period; // 근무기간 ex. 0011 or 1110
    ArrayList<Integer>week; // 근무요일  ex. 0001111
    ArrayList<Integer> time; //근무 시간 ex. 1110000
    ArrayList<Integer> gender; //성별 조건
    int age;  // 연령 조건
    int hourpay; // 시급
    ArrayList<Integer> payMethod;  //시급 지급 방식 ex. 1100

}