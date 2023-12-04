package com.example.damnbreadback.controller;

import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.HistoryService;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damnlist")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private HistoryService historyService;

    @GetMapping
    public ResponseEntity<Object> getAllPosts(@RequestParam int page) throws ExecutionException, InterruptedException, TimeoutException {
        List<PostDto> list = postService.findPosts(page-1);
        System.out.println(list);
        if(list.isEmpty()) return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
        else return ResponseEntity.ok().body(list);
    }

    @GetMapping(path="/filter")
    public ResponseEntity<Object> getPostFilter(@RequestBody PostFilter postFilter, @RequestParam int page) throws ExecutionException, InterruptedException {
        Page postPageFilter = postService.getPostFilter(postFilter, page-1);
        System.out.println(postPageFilter);

        if(postPageFilter.isEmpty())
            return ResponseEntity.badRequest().body("no filtered data");

        return ResponseEntity.ok().body(postPageFilter);
    }

    @RequestMapping(path="/{postNum}", method = RequestMethod.GET)
    public ResponseEntity<Object> getPost(@PathVariable Long postNum) throws ExecutionException, InterruptedException{

        PostDto post = postService.getPostById(postNum);
        if(post != null) return ResponseEntity.ok().body(post);
        else return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path="/{postNum}/apply", method = RequestMethod.POST)
    public ResponseEntity<Object> apply(Authentication authentication,@PathVariable Long postNum) throws ExecutionException, InterruptedException{
        if(authentication != null){
            Long newHistoryId = historyService.createHistory(postNum, userService.findUserIdById(authentication.getName()));

            if(newHistoryId == -1L){ //공고자가 지원
                return ResponseEntity.status(406).body("게시자는 지원할 수 없습니다.");
            }
            else if(newHistoryId == -2L){ // 올바르지 않은 post 정보
                return ResponseEntity.status(400).body("올바르지 않은 게시물 정보 입니다.");
            }
            else if (newHistoryId == -3L){ // 이미 지원한 공고
                return ResponseEntity.status(409).body("이미 지원한 공고입니다.");
            }
            else return ResponseEntity.ok().body(newHistoryId);
        }
        else return ResponseEntity.status(401).body("인증되지 않은 사용자 입니다.");
    }

    @RequestMapping(path="/new", method = RequestMethod.POST)
    public ResponseEntity<Object> createPost(Authentication authentication, @RequestBody Post uploadRequest) throws ExecutionException, InterruptedException {

        Long postId = postService.createPost(authentication.getName(), uploadRequest);
        if(postId == null) return new ResponseEntity<>("null exception", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<> (postId, HttpStatus.OK);
    }

    //@RequestMapping(path="/detail/bookmark", method = RequestMethod.POST)
    @PostMapping("/{postNum}/scrap")
    public ResponseEntity<Object> createScrap(Authentication authentication, @PathVariable int postNum) throws  ExecutionException, InterruptedException{
        if(authentication == null) return ResponseEntity.badRequest().body("올바르지 않은 인증입니다");

        Long userId = userService.findUserIdById(authentication.getName());
        Boolean isSuccess = postService.scrap(userId, postNum);

        if(isSuccess) return new ResponseEntity<> (HttpStatus.ACCEPTED);
        else return new ResponseEntity<>("cannot find post", HttpStatus.NOT_FOUND);
    }

    // 홈화면 다이렉트
    // 근처 땜빵
//    @RequestMapping(path = "/neardamn", method = RequestMethod.GET){
//
//    }
//
//    // 마감 임박 땜빵
//    @RequestMapping(path = "/endlinedamn", method = RequestMethod.GET){
//
//    }
//
//    // 최신 땜빵
//    @RequestMapping(path = "/newdamn", method = RequestMethod.GET){
//
//    }
//
//    // 추천 땜빵
//    @RequestMapping(path = "/recomdamn ", method = RequestMethod.GET){
//
//    }
}