package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.UserFilter;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface UserService {
    String login(String userName, String password)  throws ExecutionException, InterruptedException;
    List<User> getUsers() throws ExecutionException, InterruptedException;
    User getUserById(Long id)  throws ExecutionException, InterruptedException;

    // 로그인
    User loginCheck(String id, String pw) throws ExecutionException, InterruptedException;

    // 회원가입 -> 중복 체크
    String verifyId(String id) throws ExecutionException, InterruptedException;
    String verifyNickname(String nickname) throws ExecutionException, InterruptedException;
    String verifyEmail(String email) throws ExecutionException, InterruptedException;

    // 회원가입 -> 신규 회원 저장
    User addUser(User user) throws ExecutionException, InterruptedException ;

    // 인재정보 rank 데이터 get
    List<User> getRankScore(int page) throws ExecutionException, InterruptedException;
    Page getRankFilter(UserFilter userFilter, int page) throws ExecutionException, InterruptedException;
    Date calculateBirthDateFromAge(int age);

    String getUserId(String id) throws ExecutionException, InterruptedException;

    List<String> getBookmarks(String user) throws ExecutionException, InterruptedException;

    Long findUserIdById(String id) throws ExecutionException, InterruptedException;
}