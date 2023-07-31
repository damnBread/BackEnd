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
//@RequestMapping("/exam/svc/v1")
public class UserRankController {
    public static final String SESSION_NAME = "USER";
    @Autowired
    UserService userService;

    //인재 랭킹 3명 , 새로운 인재 20명 불러오기
    @GetMapping("/damnrank")
    public ResponseEntity<Object> getUserRank() throws ExecutionException, InterruptedException {
        List<User> rankScoreUsers = userService.getRankScore();

        System.out.println("rank : "+rankScoreUsers.get(0));

        if(rankScoreUsers.isEmpty())
            return ResponseEntity.badRequest().body("no rank data");


        return ResponseEntity.ok().body(rankScoreUsers);
    }

    //인재 상세 정보
    @PostMapping("/damnrank/detail")
    public ResponseEntity<Object> getUserDetail(Authentication authentication, HttpServletRequest request) throws ExecutionException, InterruptedException {
        User user = null;

        String userId = authentication.getName();
        user = userService.getUserById(userId);

        System.out.println(user.getId());
        if(user == null ) return  ResponseEntity.badRequest().body("can not find user");
        return ResponseEntity.ok().body(user);

//        sessionManager = new SessionManager(request);
//
//        if((user = (User)sessionManager.getSessionValue(SESSION_NAME)) == null){
//            // 사용자 찾아서 정보 전달
//        }
//        else {
//            // 세션에서 사용자 찾아오기.
//            System.out.println(user);
//        }

    }

}