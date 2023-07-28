package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.UserService;
import com.example.damnbreadback.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/exam/svc/v1")
public class UserRankController {

    SessionManager sessionManager;
    public static final String SESSION_NAME = "USER";
    @Autowired
    UserService userService;

    //인재 랭킹 3명 , 새로운 인재 20명 불러오기
    @GetMapping("/damnrank")
    public ResponseEntity<Object> getUserRank() throws ExecutionException, InterruptedException {
        //인재 랭킹 3명 불러오기 ( db에서 스코어 기준으로 내림차순 정렬 후, 3명 불러오기 -> 같은 점수면 매칭 이력이 적은 사람이 상위 )
        List<User> rankScoreUsers = userService.getRankScore();

        System.out.println(rankScoreUsers.get(0));

        if(rankScoreUsers == null)
            return ResponseEntity.badRequest().body("no rank data");


        return ResponseEntity.ok().body(rankScoreUsers);
    }

    //인재 상세 정보
    @PostMapping("/damnrank/detail")
    public ResponseEntity<Object> getUserDetail(@RequestBody String userid, HttpServletRequest request) throws ExecutionException, InterruptedException {
        User user = null;

        sessionManager = new SessionManager(request);

        if((user = (User)sessionManager.getSessionValue(SESSION_NAME)) == null){
            // 사용자 찾아서 정보 전달
        }
        else {
            // 세션에서 사용자 찾아오기.
            System.out.println(user);
        }


        return ResponseEntity.ok().body("success~~");
    }

}