package com.example.damnbreadback.controller;

import com.example.damnbreadback.config.JwtFilter;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/mypage")
public class MyPageController {
    private Long expiredMs = 1000 * 60 * 60l;
    @Autowired
    private UserService userService;
    private JwtFilter jwtFilter;
    @Autowired
    private PostService postService;

//    @PreAuthorize("hasRole('USER')") //접근권한 (우리 웹에 ADMIN이 생긴다면.. 필요할 아이)
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getMyPage(Authentication authentication, @RequestParam String userid, HttpServletRequest request, HttpServletResponse response) throws ExecutionException, InterruptedException {

        if(authentication == null) return ResponseEntity.ok().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());
        return ResponseEntity.ok().body(authentication.getName() + "님의 마이페이지 성공");

//        return ResponseEntity.ok().body("님의 마이페이지 성공");
    }

    @RequestMapping(value = "/{userid}/bookmark", method = RequestMethod.GET)
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
