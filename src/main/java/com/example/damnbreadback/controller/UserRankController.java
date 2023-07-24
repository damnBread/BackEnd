package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.UserService;
import com.example.damnbreadback.sessionManager.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/exam/svc/v1")
public class UserRankController {

    @Autowired
    UserService userService;

    //인재 랭킹 3명 , 새로운 인재 20명 불러오기
    @GetMapping("/damnrank")
    public ResponseEntity<Object> getUserRank() throws ExecutionException, InterruptedException {
        //인재 랭킹 3명 불러오기 ( db에서 스코어 기준으로 내림차순 정렬 후, 3명 불러오기 -> 같은 점수면 매칭 이력이 적은 사람이 상위 )
        List<User> rankScoreUsers = userService.getRankScore();

        if(rankScoreUsers == null)
            return ResponseEntity.badRequest().body("no rank data");

        return ResponseEntity.ok().body(rankScoreUsers);
    }

    //인재 상세 정보
//    @PostMapping("/damnrank/{userid}")
//    public ResponseEntity<Object> getUserDetail(@PathVariable id) throws ExecutionException, InterruptedException {
//
//    }

}