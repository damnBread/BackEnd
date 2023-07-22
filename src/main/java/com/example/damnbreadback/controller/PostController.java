package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.service.PostService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damnlist")
public class PostController {

    @Autowired
    private PostService postService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Object> getAllPosts() throws ExecutionException, InterruptedException {
        List<Post> list = postService.getPosts();
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
}