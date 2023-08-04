package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.UserService;
import com.example.damnbreadback.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class UserRankController {
    public static final String SESSION_NAME = "USER";
    @Autowired
    UserService userService;

    //인재 랭킹 3명 , 새로운 인재 20명 불러오기
    @GetMapping("/damnrank")
    public ResponseEntity<Object> getUserRank(@RequestParam int page) throws ExecutionException, InterruptedException {
        List<User> rankScoreUsers = userService.getRankScore(page);

        System.out.println("rank : "+rankScoreUsers.get(0));

        if(rankScoreUsers.isEmpty())
            return ResponseEntity.badRequest().body("no rank data");


        return ResponseEntity.ok().body(rankScoreUsers);
    }

    //인재 상세 정보
    @GetMapping("/damnrank/{userid}/detail")
    public ResponseEntity<Object> getUserDetail(@PathVariable("userid") Long userId) throws ExecutionException, InterruptedException {
        User user = null;

        user = userService.getUserById(userId);

        if(user == null) return  ResponseEntity.badRequest().body("can not find user");
        return ResponseEntity.ok().body(user);

    }

}