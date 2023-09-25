package com.example.damnbreadback.controller;

import com.example.damnbreadback.config.JwtFilter;
import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.HistoryService;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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

    @Autowired
    private HistoryService historyService;
    private JwtFilter jwtFilter;
    @Autowired
    private PostService postService;

    //    @PreAuthorize("hasRole('USER')") //접근권한 (우리 웹에 ADMIN이 생긴다면.. 필요할 아이)
    // 마이페이지 -> 내 정보 (첫화면)
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public ResponseEntity<Object> getMyPage(Authentication authentication) throws ExecutionException, InterruptedException {

        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        UserDTO user = userService.getUserByUserid(authentication.getName()); // 유저 기본 정보
        int careerCnt = historyService.getHistory(userService.findUserIdById(authentication.getName())).size();
        user.setCareerCnt(careerCnt);

        //TODO//스크랩

        if(user == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");
        return ResponseEntity.ok().body(user);
    }

    // 마이페이지 -> 내 정보 수정
    @RequestMapping(value = "/setting", method = RequestMethod.PATCH)
    public ResponseEntity<Object> patchMyPage(Authentication authentication, @RequestBody Map<Object, Object> fields) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        UserDTO updatedUser = userService.patchUserInfo(authentication.getName(), fields);

        if(updatedUser == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");
        return ResponseEntity.ok().body(updatedUser);
    }

    // 마이페이지 -> 내가 지원한 땜빵 목록
    @RequestMapping(value = "/applylist", method = RequestMethod.GET)
    public ResponseEntity<Object> applyList(Authentication authentication) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        List<Post> postList = historyService.getHistory(userService.findUserIdById(authentication.getName()));

        if(postList.isEmpty()) return ResponseEntity.badRequest().body("해당하는 공고 정보가 없습니다.");
        return ResponseEntity.ok().body(postList);
    }

    // 마이페이지 -> 내가 지원한 땜빵 목록 - 세부
    @RequestMapping(value = "/applylist/{damnid}", method = RequestMethod.GET)
    public ResponseEntity<Object> applyListDetail(Authentication authentication, @PathVariable Long damnId) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        if(postService.getPostById(damnId).isPresent()){
            Post post = postService.getPostById(damnId).get();
            return ResponseEntity.ok().body(post);
        }
        else {
            return ResponseEntity.badRequest().body("해당하는 공고 정보가 없습니다.");
        }
    }


    // 마이페이지 -> 내가 의뢰한 땜빵 목록
    @RequestMapping(value = "/requestlist", method = RequestMethod.GET)
    public ResponseEntity<Object> requestList(Authentication authentication) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        List<Post> postList = postService.getPostByPublisher(userService.findUserIdById(authentication.getName()));

        if(postList == null) return ResponseEntity.badRequest().body("의뢰한 땜빵이 없습니다.");
        return ResponseEntity.ok().body(postList);
    }

    // 마이페이지 -> 내가 의뢰한 땜빵 목록 - 세부
    @RequestMapping(value = "/requestlist/{damnid}", method = RequestMethod.GET)
    public ResponseEntity<Object> requestListDetail(Authentication authentication, @PathVariable Long damnid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        Post post = postService.getPostById(damnid).get();

        if(post == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");
        return ResponseEntity.ok().body(post);
    }

    // 마이페이지 -> 내가 의뢰한 땜빵 삭제
    @RequestMapping(value = "/requestlist/{damnid}", method = RequestMethod.DELETE)
    public ResponseEntity<Object> deleteRequest(Authentication authentication, @PathVariable Long damnid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        return ResponseEntity.ok().body(postService.removePost(damnid));
    }

    // 마이페이지 -> 내가 의뢰한 땜빵 내용 수정
    @RequestMapping(value = "/requestlist/{damnid}", method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateRequest(Authentication authentication, @PathVariable Long damnid, @RequestBody Map<Object, Object> fields ) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        Post updatedPost = postService.patchPostInfo(damnid, fields);

        if(updatedPost == null) return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
        return ResponseEntity.ok().body(updatedPost);
    }

    // 마이페이지 -> 내가 의뢰한 땜빵 상태 수정
    @RequestMapping(value = "/requestlist/{damnid}/{status_code}", method = RequestMethod.PATCH)
    public ResponseEntity<Object> updateStatusRequest(Authentication authentication, @PathVariable Long damnid,@PathVariable Integer status_code) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        Long userid = userService.findUserIdById(authentication.getName());

        HistoryDto updatedHistory = historyService.patchStatus(damnid, userid, status_code);
        System.out.println("hahahah !!?!?!? -- "+updatedHistory);

        if(updatedHistory == null) return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
        return ResponseEntity.ok().body(updatedHistory);
    }

    // 마이페이지 -> 내가 의뢰한 땜빵 - 지원자 보기 목록
    @RequestMapping(value = "/requestlist/{damnid}/appliance", method = RequestMethod.GET)
    public ResponseEntity<Object> getApplianceList(Authentication authentication, @PathVariable Long damnid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        List<UserDTO> userList = historyService.getUserByHistory(damnid);

        if(userList == null) return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
        return ResponseEntity.ok().body(userList);
    }

    // 마이페이지 -> 내가 의뢰한 땜빵 - 지원자 보기 세부
    @RequestMapping(value = "/requestlist/appliance/profile/{userid}", method = RequestMethod.GET)
    public ResponseEntity<Object> getApplianceDetail(Authentication authentication, @PathVariable Long userid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        System.out.println(authentication.getName());

        UserDTO user = userService.getUserById(userid);

        if(user == null) return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
        return ResponseEntity.ok().body(user);
    }

//    // 마이페이지 -> 내가 의뢰한 땜빵 - 지원자 채팅
//    @RequestMapping(value = "/requestlist/{damnid}/appliance/chat", method = RequestMethod.POST)
//    public ResponseEntity<Object> startChat(Authentication authentication, @PathVariable Long damnid) throws ExecutionException, InterruptedException {
//        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
//        System.out.println(authentication.getName());
//
//        List<UserDTO> userList = historyService.getUserByHistory(damnid);
//
//        if(userList == null) return ResponseEntity.badRequest().body("잘못된 공고 정보입니다.");
//        return ResponseEntity.ok().body(userList);
//    }




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
