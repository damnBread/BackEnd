package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.entity.SignupRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {

    String login(String userName, String password)  throws ExecutionException, InterruptedException;
    List<User> getUsers() throws ExecutionException, InterruptedException;
    User getUserByUserId(String id)  throws ExecutionException, InterruptedException;

    // 로그인
    User loginCheck(String id, String pw) throws ExecutionException, InterruptedException;

    // 회원가입 -> 중복 체크
    String verifyId(String id) throws ExecutionException, InterruptedException;
    String verifyNickname(String nickname) throws ExecutionException, InterruptedException;
    String verifyEmail(String email) throws ExecutionException, InterruptedException;

    // 회원가입 -> 신규 회원 저장
    User addUser(SignupRequest signupRequest) throws ExecutionException, InterruptedException ;

    // 인재정보 rank 데이터 get
    List<User> getRankScore() throws ExecutionException, InterruptedException;


    String getUserId(String id) throws ExecutionException, InterruptedException;

    List<String> getBookmarks(String user) throws ExecutionException, InterruptedException;
}