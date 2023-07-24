package com.example.damnbreadback.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class User {
    private Date joinDate; // 가입한 날짜
    private String id;
    private String name; // 이름
    private String email; // 이메일
    private String nickname; // 닉네임
    private String password; // 비밀번호
    private String phone; // 전화번호
    private String home; // 거주지 -> string 으로 (private String location)

    // firebase timestamp type
    private Date birth; // 생년월일
    private boolean gender; // 성별 (true : 남자 , false : 여자)
    private int noShow; // 노쇼 횟수
    private int score; // 점수
    private HashMap<String, Integer> career; // 경력 사항
    private ArrayList<String> hopeJob; // 희망 업/직종
    private ArrayList<String> hopeLocation; // 희망 거주지 -> String Array
    private HashMap<String, Boolean> isPublic; // 정보 공개 여부

    private ArrayList<History> history; // 땜빵 이력

    private ArrayList<String> scrap; // 북마크 게시물


}