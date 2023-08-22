package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.entity.UserFilter;
import com.example.damnbreadback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damnrank")
public class UserRankController {
//    public static final String SESSION_NAME = "USER";

    @Autowired
    UserService userService;

    //인재 랭킹 3명 , 새로운 인재 20명 불러오기
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getUserRank(@RequestParam int page) throws ExecutionException, InterruptedException {
        List<User> rankScoreUsers = userService.getRankScore(page-1);

        if(rankScoreUsers.isEmpty())
            return ResponseEntity.badRequest().body("no rank data");


        return ResponseEntity.ok().body(rankScoreUsers);
    }

    //인재 상세 정보
    @RequestMapping(value = "/{userid}/detail", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserDetail(@PathVariable("userid") Long userId) throws ExecutionException, InterruptedException {
        User user = null;

        user = userService.getUserById(userId);

        if(user == null) return  ResponseEntity.badRequest().body("can not find user");
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(value = "/filter", method = RequestMethod.GET)
    public ResponseEntity<Object> getUserFilter(@RequestBody UserFilter userFilter, @RequestParam int page) throws ExecutionException, InterruptedException {
        Page rankScoreUsers = userService.getRankFilter(userFilter, page-1);

        if(rankScoreUsers.isEmpty())
            return ResponseEntity.badRequest().body("no filtered data");

        return ResponseEntity.ok().body(rankScoreUsers);
    }

}