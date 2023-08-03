package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.service.UserService;
import com.example.damnbreadback.session.SessionManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/exam/svc/v1")
public class UserController {
//    public static final String SESSION_NAME = "USER";
//    private SessionManager sessionManager;
//
    @Autowired
    private UserService userService;

//    @GetMapping("/users")
//    public ResponseEntity<Object> getUsers() throws ExecutionException, InterruptedException {
//        List<User> list = userService.getUsers();
//        return ResponseEntity.ok().body(list);
//    }

//
//
//    //단순 쿠키 + 세션 방식===============================================================
//    // 쿠키에는 세션 ID가 저장되어있음
//    // 1. 웹브라우저가 서버에 요청 전송 (쿠키가 있다면 함께 전송)
//    // 2. 웹브라우저로부터 쿠키를 받았다면 : 쿠키에 들어있는 세션ID 정보를 이용해서 session에서 user정보를 바로 받아와서 정보 제공
//    //    웹브라우저로부터 쿠키를 받지 않았다면 : 로그인 요청 들어온 user정보를 확인하고, 로그인 정보가 맞다면 세션에 user정보 저장, 저장된 session의 ID를 담은 쿠키를 만들어서 response와 함께 전달.
//    // 3. 다음부터는 웹브라우저가 서버에 요청을 할 때 쿠키를 함께 전달.
//
//    // 세션 SSID 생성 -> 쿠키에 USER로 저장 / 세션에 SSID : User객체 형식으로 저장
//    // -> 쿠키가 넘어오면 USER에 해당하는 쿠키의 VALUE(서버에서 만들어준 쿠키 SSID) 받아오기
//    // -> 세션에서 VALUE에 맞는 세션 VALUE 받아오기
//    //================================================================================
//
//    //===================================================================================
//    //JWT + Spring Security 방식
//    // 서버 - Spring Security를 이용하여 인가 -> login , signup 페이지는 인증이 필요없도록 / 나머지 페이지는 USER 인증 후에만 사용가능
//    // 1. 웹브라우저가 서버에 로그인 요청
//    // 2. 서버는 로그인 정보 확인 (Valid Id?? / Valid Pw?? )
//    // 3. 올바른 로그인 정보 확인 후 -> Jwt 토큰을 만들어 (본 파일 중에서는 JwtUtils 기능)서 클라이언트에게 전달
//    // 4. ex. 서버로 마이페이지 접근 요청 -> 부여받은 토큰이 있다면 (로그인 된 사용자라면) 함께 전달
//    // 5. 서버는 토큰을 확인 -> 토큰이 있는 인증된 사용자 : 사용자 정보를 가져와서 마이페이지를 보여줌
//    //                    -> 토큰이 없는 인증되지 않은 사용자 : 로그인 화면으로 이동하거나 로그인(인증)이 필요하다는 메세지 등을 통해 사용자에게 알림.
//    //===================================================================================

//    //test : zara0140 / 1234567a
    @PostMapping("/login")
    public ResponseEntity<Object> loginRequest(@RequestBody LoginRequest loginRequest, HttpServletRequest request,  HttpServletResponse response) throws ExecutionException, InterruptedException {
        String tok = userService.login(loginRequest.getId(), loginRequest.getPw());

        if(tok.equals("fail to find user"))
            return ResponseEntity.badRequest().body("fail to find user");
        if(tok.equals("db null exception"))
            return ResponseEntity.badRequest().body("null exception");

        response.addHeader("Authorization", "Bearer " + tok);
        return ResponseEntity.ok().body(tok);
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> signupRequest(@RequestBody SignupRequest signupRequest) throws ExecutionException, InterruptedException {
        User user =  userService.addUser(signupRequest);
        if(user == null) return new ResponseEntity<Object>("null exception", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(user, HttpStatus.CREATED);
    }


    @PostMapping("/signup/verify/id")
    public ResponseEntity<Object> verifyId(@RequestBody Map<String, String> id) throws ExecutionException, InterruptedException{
        String verifyResult = userService.verifyId(id.get("id"));
        if(verifyResult.equals("null exception")) return ResponseEntity.badRequest().body("null exception");
        else return ResponseEntity.ok().body(verifyResult);
    }

    @PostMapping("/signup/verify/nickname")
    public ResponseEntity<Object> verifyNickname(@RequestBody Map<String, String> nickname) throws ExecutionException, InterruptedException{
        String verifyResult = userService.verifyNickname(nickname.get("nickname"));
        if(verifyResult.equals("null exception")) return ResponseEntity.badRequest().body("null exception");
        else return ResponseEntity.ok().body(verifyResult);
    }

    @PostMapping("/signup/verify/email")
    public ResponseEntity<Object> verifyEmail(@RequestBody Map<String, String> email) throws ExecutionException, InterruptedException{
        String verifyResult = userService.verifyEmail(email.get("email"));
        if(verifyResult.equals("null exception")) return ResponseEntity.badRequest().body("null exception");
        else return ResponseEntity.ok().body(verifyResult);
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "logout sucess";
    }

}