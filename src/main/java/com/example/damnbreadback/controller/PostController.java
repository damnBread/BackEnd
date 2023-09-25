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
        Page<Post> postPage = postService.findPosts(page-1);
        List<Post> list = postPage.getContent();
        if(list.isEmpty()) return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
        return ResponseEntity.ok().body(list);
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

        Optional<Post> post = postService.getPostById(postNum);
        if(post.isPresent()) return ResponseEntity.ok().body(post);
        else return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
    }

    @RequestMapping(path="/{postNum}/apply", method = RequestMethod.POST)
    public ResponseEntity<Object> apply(Authentication authentication,@PathVariable Long postNum) throws ExecutionException, InterruptedException{

        Long newHistoryId = historyService.createHistory(postNum, userService.findUserIdById(authentication.getName()));

        if(newHistoryId == null){
            return new ResponseEntity<>("null exception", HttpStatus.BAD_REQUEST);
        }
        else  return ResponseEntity.ok().body(newHistoryId);
    }

    @RequestMapping(path="/new", method = RequestMethod.POST)
    public ResponseEntity<Object> createPost(Authentication authentication, @RequestBody Post uploadRequest) throws ExecutionException, InterruptedException {

        Long postId = postService.createPost(authentication.getName(), uploadRequest);
        if(postId == null) return new ResponseEntity<>("null exception", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<> (postId, HttpStatus.OK);
    }

    //@RequestMapping(path="/detail/bookmark", method = RequestMethod.POST)
    @PostMapping("/{postNum}/bookmark")
    public ResponseEntity<Object> bookmark(Authentication authentication, @PathVariable int postNum) throws  ExecutionException, InterruptedException{
        Boolean isSuccess = postService.bookmark(authentication.getName(), postNum);

        if(isSuccess) return new ResponseEntity<> (HttpStatus.ACCEPTED);
        else return new ResponseEntity<>("cannot find post", HttpStatus.NOT_FOUND);
    }
}