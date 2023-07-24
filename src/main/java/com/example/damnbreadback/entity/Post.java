package com.example.damnbreadback.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Post {

    private String branchName; // 점포명
    private String location; // 근무지역
    private int hourPay; // 시급
    private boolean payMethod; // 임금 지불 방법 (true : 의뢰인 직접 지급 / false : 당일 현장 지급)
    private ArrayList<String> job; // 업/직종

    private Date workStart; // 근무 시작 일시
    private Date workFinish; // 근무 종료 일시
    private int workPeriod; // 근무 기간

    private int applicantCount; // 지원자 수
    private int viewCount; // 조회 수

    private String title; // 공고 제목
    private String content; // 공고 내용

    private String publisher; // 게시자
    private Date publishDate; // 게시일
    private Date deadline; // 마감일

    private boolean genderLimit; //성별 조건 (true : 남자, false : 여자)
    private HashMap<String , Integer> ageLimit; // 나이 조건
    private int careerLimit; // 해당 업직종에 대한 경력 조건

}