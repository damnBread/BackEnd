package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.Noshow;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.dto.SignupRequest;
import com.example.damnbreadback.dto.UserFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public interface UserService {
    Map<Long, String> login(String userName, String password, HttpServletResponse response)  throws ExecutionException, InterruptedException;
    String logout(HttpServletRequest request) throws ExecutionException, InterruptedException, AccessDeniedException;
    UserDTO getUserById(Long id)  throws ExecutionException, InterruptedException;

    // 로그인
    UserDTO loginCheck(String id, String pw) throws ExecutionException, InterruptedException;

    // 회원가입 -> 중복 체크
    String verifyId(String id) throws ExecutionException, InterruptedException;
    String verifyNickname(String nickname) throws ExecutionException, InterruptedException;
    String verifyEmail(String email) throws ExecutionException, InterruptedException;

    // 회원가입 -> 신규 회원 저장
    UserDTO addUser(UserDTO user) throws ExecutionException, InterruptedException ;

    UserDTO patchUserInfo(String id, Map<Object, Object> fields) throws ExecutionException, InterruptedException ;

    // 인재정보 rank 데이터 get
    List<UserDTO> getRankScore(int page) throws ExecutionException, InterruptedException;
    Page getRankFilter(UserFilter userFilter, int page) throws ExecutionException, InterruptedException;
    Date calculateBirthDateFromAge(int age);

    UserDTO getUserByUserid(String id) throws ExecutionException, InterruptedException;

    Long findUserIdById(String id) throws ExecutionException, InterruptedException;

    // 노쇼 신고학
    Noshow reportNoshow(Long damnId, Long userId) throws ExecutionException, InterruptedException;
}