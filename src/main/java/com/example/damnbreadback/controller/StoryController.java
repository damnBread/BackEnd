package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.service.PostService;
import com.example.damnbreadback.service.StoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/damnstory")
public class StoryController {

//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/users")
//    public ResponseEntity<Object> getUsers() throws ExecutionException, InterruptedException {
//        System.out.println("rrrrrrrrrrrrr");
//        List<User> list = userService.getUsers();
//        return ResponseEntity.ok().body(list);
//
//    }

    @Autowired
    private StoryService storyService;

    @GetMapping
    public ResponseEntity<Object> getAllStories() throws ExecutionException, InterruptedException {

        List<Story> list = storyService.getAllStories();
        return ResponseEntity.ok().body(list);
    }



}