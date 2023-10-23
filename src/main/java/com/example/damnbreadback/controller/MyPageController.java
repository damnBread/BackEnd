package com.example.damnbreadback.controller;

import com.example.damnbreadback.config.JwtFilter;
import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.*;
import com.example.damnbreadback.service.*;
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
    private CareerService careerService;
    @Autowired
    private ScrapService scrapService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private PostService postService;

    //    @PreAuthorize("hasRole('USER')") //접근권한 (우리 웹에 ADMIN이 생긴다면.. 필요할 아이)
    // 마이페이지 -> 내 정보 (첫화면)
    @RequestMapping(value = "/setting", method = RequestMethod.GET)
    public ResponseEntity<Object> getMyPage(Authentication authentication) throws ExecutionException, InterruptedException {

        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");
        UserDTO user = userService.getUserByUserid(authentication.getName()); // 유저 기본 정보
        List<Career> careers = careerService.getCareers(user.getUserId());
        user.setCareerCnt(careers.size());

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

        List<PostDto> postList = historyService.getHistory(userService.findUserIdById(authentication.getName()));

        if(postList.isEmpty()) return ResponseEntity.badRequest().body("해당하는 공고 정보가 없습니다.");
        return ResponseEntity.ok().body(postList);
    }

    // 마이페이지 -> 내가 지원한 땜빵 목록 - 세부
    @RequestMapping(value = "/applylist/{damnid}", method = RequestMethod.GET)
    public ResponseEntity<Object> applyListDetail(Authentication authentication, @PathVariable Long damnId) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        if(postService.getPostById(damnId) != null){
            PostDto post = postService.getPostById(damnId);
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

        List<PostDto> postList = postService.getPostByPublisher(userService.findUserIdById(authentication.getName()));

        if(postList == null) return ResponseEntity.badRequest().body("의뢰한 땜빵이 없습니다.");
        return ResponseEntity.ok().body(postList);
    }

    // 마이페이지 -> 내가 의뢰한 땜빵 목록 - 세부
    @RequestMapping(value = "/requestlist/{damnid}", method = RequestMethod.GET)
    public ResponseEntity<Object> requestListDetail(Authentication authentication, @PathVariable Long damnid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        PostDto post = postService.getPostById(damnid);

        if(post == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");
        return ResponseEntity.ok().body(post);
    }

    //마이페이지 -> 내가 의뢰한 땜빵 목록 - 리뷰 남기기
    @RequestMapping(value = "/requestlist/{damnid}/review", method = RequestMethod.POST)
    public ResponseEntity<Object> reportReview(Authentication authentication, @PathVariable Long damnid, @RequestBody Map<String, String> badge) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        //리뷰 남기기
        User user = postService.reportReview(damnid, badge.get("badge"));

        if(user == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");
        return ResponseEntity.ok().build();
    }

    //마이페이지 -> 내가 의뢰한 땜빵 목록 - 매칭 확정하기.
    @RequestMapping(value = "/requestlist/{damnid}/{userid}/match", method = RequestMethod.POST)
    public ResponseEntity<Object> matchUser(Authentication authentication, @PathVariable Long damnid, @PathVariable Long userid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        String result = "";

        result = postService.matchUser(damnid, userid) ;

        if(result.equals("매칭이 확정되었습니다.")) return ResponseEntity.status(201).body(result);
        else if(result.equals("매칭이 변경되었습니다.")) return ResponseEntity.status(202).body(result);
        else return ResponseEntity.badRequest().body("사용자를 매칭할 수 없습니다.");
    }

    //마이페이지 -> 내가 의뢰한 땜빵 목록 - 리뷰남기기 - 노쇼 신고하기
    @RequestMapping(value = "/requestlist/{damnid}/{userid}/noshow", method = RequestMethod.POST)
    public ResponseEntity<Object> reportNoshow(Authentication authentication, @PathVariable Long damnid, @PathVariable Long userid) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        Noshow noshow = userService.reportNoshow(damnid, userid);

        if(noshow == null) return ResponseEntity.badRequest().body("잘못된 유저 정보입니다.");

        return ResponseEntity.ok().build();
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

        PostDto updatedPost = postService.patchPostInfo(damnid, fields);

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

    //스크랩한 공고 가져오기
    @RequestMapping(value = "/scrap", method = RequestMethod.GET)
    public ResponseEntity<Object> getScarps(Authentication authentication) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        List<PostDto> scrapPosts = scrapService.getScraps(userService.findUserIdById(authentication.getName()));

        if(scrapPosts.isEmpty()) return ResponseEntity.noContent().build();

        System.out.println(scrapPosts);

        return ResponseEntity.ok().body(scrapPosts);
//        return ResponseEntity.ok().body("scrapPost");
    }

    //땜빵 이력 가져오기
    @RequestMapping(value = "/career", method = RequestMethod.GET)
    public ResponseEntity<Object> getCareers(Authentication authentication) throws ExecutionException, InterruptedException {
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        List<Career> careers = careerService.getCareers(userService.findUserIdById(authentication.getName()));
        return ResponseEntity.ok().body(careers);
    }
}
