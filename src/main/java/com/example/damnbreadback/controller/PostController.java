package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.UserService;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/exam/svc/v1")
public class PostController {

//    @Autowired
//    private UserService userService;
//
//    @GetMapping("/posts")
//    public ResponseEntity<Object> getUsers() throws ExecutionException, InterruptedException {
//        System.out.println("rrrrrrrrrrrrr");
//        List<User> list = userService.getUsers();
//        return ResponseEntity.ok().body(list);
//
//    }


}