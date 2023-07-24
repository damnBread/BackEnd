package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.User;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {

    List<User> getUsers() throws ExecutionException, InterruptedException;

    // 로그인
    User loginCheck(String id, String pw) throws ExecutionException, InterruptedException;

    // 회원가입 -> 중복 체크
    Boolean verifyId(String id) throws ExecutionException, InterruptedException;
    Boolean verifyNickname(String nickname) throws ExecutionException, InterruptedException;
    Boolean verifyEmail(String email) throws ExecutionException, InterruptedException;

    // 회원가입 -> 신규 회원 저장
    User addUser(SignupRequest signupRequest) throws ExecutionException, InterruptedException ;

    // 인재정보 rank 데이터 get
    List<User> getRankScore() throws ExecutionException, InterruptedException;


    String getUserId(String id) throws ExecutionException, InterruptedException;

    List<String> getBookmarks(String user) throws ExecutionException, InterruptedException;
}