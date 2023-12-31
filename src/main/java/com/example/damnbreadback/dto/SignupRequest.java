package com.example.damnbreadback.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Getter
@Setter
public class SignupRequest {
    private String id;
    private String pw; // 비밀번호
    private String name; // 이름
    private String email; // 이메일
    private String nickname; // 닉네임
    private String phone; // 전화번호
    private String home; // 거주지 -> string 으로 (private String location)

    // firebase timestamp type
    private Date birth; // 생년월일
    private boolean gender; // 성별 (true : 남자 , false : 여자)
    private String hopeJob; // 희망 업/직종
    private String hopeLocation; // 희망 거주지 -> String Array
}