package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Object> getAllPosts(@RequestParam int page) throws ExecutionException, InterruptedException, TimeoutException {
        Page<Post> postPage = postService.findStories(page-1);
        List<Post> list = postPage.getContent();
        if(list.isEmpty()) return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(path="/new", method = RequestMethod.POST)
    public ResponseEntity<Object> createPost(@RequestBody Post uploadRequest) throws ExecutionException, InterruptedException {

        Long postId = postService.createPost(uploadRequest);
        if(postId == null) return new ResponseEntity<>("null exception", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<> (postId, HttpStatus.OK);
    }

    @RequestMapping(path="/detail", method = RequestMethod.GET)
    public ResponseEntity<Object> getPost(@RequestParam Long postNum) throws ExecutionException, InterruptedException{

        Optional<Post> post = postService.getPostById(postNum);
        if(post.isPresent()) return ResponseEntity.ok().body(post);
        else return new ResponseEntity<>("null exception", HttpStatus.NO_CONTENT);
    }

    //@RequestMapping(path="/detail/bookmark", method = RequestMethod.POST)
    @PostMapping("/detail/bookmark")
    public ResponseEntity<Object> bookmark(@RequestBody String userId, @RequestBody String postNum) throws  ExecutionException, InterruptedException{


        return ResponseEntity.ok().body(null);
    }
}