package com.example.damnbreadback.controller;

import com.example.damnbreadback.config.JwtFilter;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import org.springframework.security.core.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class MyPageController {
    private Long expiredMs = 1000 * 60 * 60l;
    @Autowired
    private UserService userService;
    private JwtFilter jwtFilter;
    @Autowired
    private PostService postService;

    @GetMapping("/mypage")
    public ResponseEntity<Object> getMyPage(@RequestParam String userid, Authentication authentication, HttpServletRequest request, HttpServletResponse response) throws ExecutionException, InterruptedException {
//        System.out.println(authentication.getName());
        return ResponseEntity.ok().body("님의 마이페이지 성공");
//        return ResponseEntity.ok().body(authentication.getName() + "님의 마이페이지 성공");
    }

    @GetMapping("/mypage/{userid}/bookmark")
    public ResponseEntity<Object> getBookmarks(@PathVariable String userid) throws ExecutionException, InterruptedException {
        String user = userService.getUserId(userid);
        List<String> bookmarked = userService.getBookmarks(user);
        ArrayList<Post> bookmarkedPosts = new ArrayList<Post>();
        for (String bookmarkedPost:bookmarked) {
            bookmarkedPosts.add(postService.getPostById(bookmarkedPost));
        }

        return ResponseEntity.ok().body(bookmarkedPosts);
    }
}
