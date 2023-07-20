package com.example.damnbreadback.controller;

import com.example.damnbreadback.SessionManager.SessionManager;
import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.UserService;
import com.google.api.Http;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/exam/svc/v1")
public class UserController {

    @Autowired
    private final SessionManager sessionManager;
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() throws ExecutionException, InterruptedException {
        List<User> list = userService.getUsers();
        return ResponseEntity.ok().body(list);

    }


    //test : zara0140 / 1234567a
    @GetMapping("/login")
    public ResponseEntity<Object> loginRequest(@RequestBody LoginRequest loginRequest, HttpServletResponse response) throws ExecutionException, InterruptedException {
        User user = null;
//        session.setMaxInactiveInterval(1800); // 1800초


        user = userService.loginCheck(loginRequest.getId(), loginRequest.getPw());
            if(user != null){
                //세션에 등록
                sessionManager.createSession(user, response);

            }

        return ResponseEntity.ok().body(user);
    }


    @PostMapping("/signup")
    public ResponseEntity<User> signupRequest(@RequestBody SignupRequest signupRequest) throws ExecutionException, InterruptedException {
        User user = new User();

        user.setId(signupRequest.getId());
        user.setPassword(signupRequest.getPw());
        user.setName(signupRequest.getName());
        user.setEmail(signupRequest.getEmail());
        user.setNickname(signupRequest.getNickname());
        user.setPhone(signupRequest.getPhone());
        user.setBirth(signupRequest.getBirth());
        user.setGender(signupRequest.isGender());
        user.setHopeJob(signupRequest.getHopeJob());
        user.setHopeLocation(signupRequest.getHopeLocation());
        user.setLocation(signupRequest.getLocation());

        user.setNoShow(0);
        user.setScore(0);
        user.setCareer(null);

        HashMap<String, Boolean> isPublicMap = new HashMap<>();
        isPublicMap.put("birth", true);
        isPublicMap.put("career", true);
        isPublicMap.put("email", true);
        isPublicMap.put("gender", true);
        isPublicMap.put("hope-job", true);
        isPublicMap.put("location", true);
        isPublicMap.put("name", true);
        isPublicMap.put("phone", true);

        user.setIsPublic(isPublicMap);

        userService.addUser(user);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }
}