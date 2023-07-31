package com.example.damnbreadback.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue
    private Long userId; // 기본키

    private Date joinDate; // 가입한 날짜

    private String name; // 이름
    private String nickname; // 닉네임

    private String id; // 아이디
    private String password; // 비밀번호

    private String email; // 이메일
    private String phone; // 전화번호
    private String home; // 거주지 -> string 으로 (private String location)

    private Date birth; // 생년월일
    private boolean gender; // 성별 (true : 남자 , false : 여자)

    private int noShow; // 노쇼 횟수
    private int score; // 점수

    private String hopeJob; // 희망 업/직종
    private String hopeLocation; // 희망 근무지역 -> String Array

    private String isPublic; // 정보 공개 여부 "0101011" < 이런 식으로 이진값을 받아야할듯

    @ManyToMany( fetch = FetchType.EAGER, mappedBy = "scrapUsers")
    private Set<Post> scraps; // 스크랩한 포스트 목록

    @OneToMany(fetch=FetchType.EAGER)
    private List<Career> career; // 경력

    @OneToMany(fetch = FetchType.EAGER)
    private List<History> histories; // 땜빵이력
}