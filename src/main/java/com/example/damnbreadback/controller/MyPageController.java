package com.example.damnbreadback.controller;

import com.example.damnbreadback.config.JwtFilter;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.Post;
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
@RequestMapping("/mypage")
public class MyPageController {
    private Long expiredMs = 1000 * 60 * 60l;
    @Autowired
    private UserService userService;
    private JwtFilter jwtFilter;
    @Autowired
    private PostService postService;

//    @PreAuthorize("hasRole('USER')") //접근권한 (우리 웹에 ADMIN이 생긴다면.. 필요할 아이)
    // 마이페이지 -> 내 정보 (첫화면)
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public ResponseEntity<Object> getMyPage(Authentication authentication) throws ExecutionException, InterruptedException {

        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        UserDTO user = userService.getUserByUserid(authentication.getName());
        if(user == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");
        return ResponseEntity.ok().body(user);
    }

    // 마이페이지 -> 내 정보 수정
    @RequestMapping(value = "/setting", method = RequestMethod.PATCH)
    public ResponseEntity<Object> patchMyPage(Authentication authentication, @RequestBody UserDTO user) throws ExecutionException, InterruptedException {

        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(user);
        System.out.println(authentication.getName());
//        UserDTO updatedUser = userService.patchUserInfo(authentication.getName(), user);
        userService.patchUserInfo(authentication.getName(), user);
        UserDTO updatedUser = null;
        if(updatedUser == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");
        return ResponseEntity.ok().body(updatedUser);

//        return ResponseEntity.ok().body("님의 마이페이지 성공");
    }

    @RequestMapping(value = "/{userid}/bookmark", method = RequestMethod.GET)
    public ResponseEntity<Object> getBookmarks(@PathVariable String userid) throws ExecutionException, InterruptedException {
        String user = userService.getUserId(userid);
        List<String> bookmarked = userService.getBookmarks(user);
        ArrayList<Post> bookmarkedPosts = new ArrayList<Post>();
        for (String bookmarkedPost:bookmarked) {
            //bookmarkedPosts.add(postService.getPostById(bookmarkedPost));
        }

        return ResponseEntity.ok().body(bookmarkedPosts);
    }
}
