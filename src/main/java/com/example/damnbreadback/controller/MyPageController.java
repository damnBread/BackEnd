package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class MyPageController {

    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @GetMapping("/mypage/bookmark")
    public ResponseEntity<Object> getBookmarks(@CookieValue String USER, HttpServletRequest request,  HttpServletResponse response) throws ExecutionException, InterruptedException {
//        String user = userService.getUserId(userid); // 세션에서 user 객체 가져오기
//        List<String> bookmarked = userService.getBookmarks(user); // user 객체에서 scrap arraylist 뽑기
//        ArrayList<Post> bookmarkedPosts = new ArrayList<Post>(); // scrap arraylist 통해서 post 컬랙션 뒤지고 객체로 가져오기
//        for (String bookmarkedPost:bookmarked) {
//            bookmarkedPosts.add(postService.getPost(bookmarkedPost));
//        }
//
//        return ResponseEntity.ok().body(bookmarkedPosts);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER);
        //System.out.println("유저아이디" + user.getId());

        ArrayList<String> scraps = user.getScrap();
        ArrayList<Post> bookmarkedPosts = new ArrayList<Post>(); // scrap arraylist 통해서 post 컬랙션 뒤지고 객체로 가져오기
        for (String bookmarkedPost:scraps) {
            bookmarkedPosts.add(postService.getPost(bookmarkedPost));
        }

        return ResponseEntity.ok().body(bookmarkedPosts);

    }
}
