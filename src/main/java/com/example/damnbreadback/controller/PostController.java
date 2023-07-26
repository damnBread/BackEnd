package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damnlist")
public class PostController {

    @Autowired
    private PostService postService;

    // "localhost:8080/damnlist"
    @GetMapping
    public ResponseEntity<Object> getAllPosts(@RequestParam Integer page) throws ExecutionException, InterruptedException, TimeoutException {
        // Pagination 추가
        List<Post> list = postService.getPosts(page.intValue());
        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(path="/new", method = RequestMethod.POST)
    public ResponseEntity<Object> createPost(@RequestBody Post post) throws ExecutionException, InterruptedException {

        postService.createPost(post);
        return new ResponseEntity<> (post, HttpStatus.OK);
    }

//    @RequestMapping(path="/{postNum}", method = RequestMethod.GET)
//    public ResponseEntity<Object> getPost(@PathVariable String postNum) throws ExecutionException, InterruptedException{
//
//        Post post = postService.getPost(postNum);
//        return ResponseEntity.ok().body(post);
//    }

    @RequestMapping(path="/detail", method = RequestMethod.GET)
    public ResponseEntity<Object> getPost(@RequestParam String postNum) throws ExecutionException, InterruptedException{

        Post post = postService.getPost(postNum);
        return ResponseEntity.ok().body(post);
    }

    //@RequestMapping(path="/detail/bookmark", method = RequestMethod.POST)
    @PostMapping("/detail/bookmark")
    public ResponseEntity<Object> bookmark(@RequestBody String userId, @RequestBody String postNum) throws  ExecutionException, InterruptedException{
        //HttpSession session = request.getSession();


        return ResponseEntity.ok().body(null);
    }

    //@RequestMapping(path="/filter", method = RequestMethod.GET)
    //public ResponseEntity<Object> filtering(@RequestParam String)
}