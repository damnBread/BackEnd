package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.service.PostService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<Object> getUsers() throws ExecutionException, InterruptedException {
        List<Post> list = postService.getPosts();
        return ResponseEntity.ok().body(list);
    }


}