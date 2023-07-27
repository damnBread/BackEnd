package com.example.damnbreadback.controller;

import com.example.damnbreadback.config.JwtFilter;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import com.google.api.Authentication;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<Object> getMyPage(Authentication authentication) throws ExecutionException, InterruptedException {
        return ResponseEntity.ok().body(authentication.getAllFields() + "님의 마이페이지 성공");
    }

    @GetMapping("/mypage/{userid}/bookmark")
    public ResponseEntity<Object> getBookmarks(@PathVariable String userid) throws ExecutionException, InterruptedException {
        String user = userService.getUserId(userid);
        List<String> bookmarked = userService.getBookmarks(user);
        ArrayList<Post> bookmarkedPosts = new ArrayList<Post>();
        for (String bookmarkedPost:bookmarked) {
            bookmarkedPosts.add(postService.getPost(bookmarkedPost));
        }

        return ResponseEntity.ok().body(bookmarkedPosts);
    }
}
