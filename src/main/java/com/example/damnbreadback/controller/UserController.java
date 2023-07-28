package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.UserService;
import jakarta.servlet.http.Cookie;
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
    public static final String SESSION_NAME = "USER";

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public ResponseEntity<Object> getUsers() throws ExecutionException, InterruptedException {
        List<User> list = userService.getUsers();
        return ResponseEntity.ok().body(list);

    }


    // 쿠키에는 세션 ID가 저장되어있음
    // 1. 웹브라우저가 서버에 요청 전송 (쿠키가 있다면 함께 전송)
    // 2. 웹브라우저로부터 쿠키를 받았다면 : 쿠키에 들어있는 세션ID 정보를 이용해서 session에서 user정보를 바로 받아와서 정보 제공
    //    웹브라우저로부터 쿠키를 받지 않았다면 : 로그인 요청 들어온 user정보를 확인하고, 로그인 정보가 맞다면 세션에 user정보 저장, 저장된 session의 ID를 담은 쿠키를 만들어서 response와 함께 전달.
    // 3. 다음부터는 웹브라우저가 서버에 요청을 할 때 쿠키를 함께 전달.
    
    // 세션 SSID 생성 -> 쿠키에 USER로 저장 / 세션에 SSID : User객체 형식으로 저장
    // -> 쿠키가 넘어오면 USER에 해당하는 쿠키의 VALUE(서버에서 만들어준 쿠키 SSID) 받아오기 
    // -> 세션에서 VALUE에 맞는 세션 VALUE 받아오기

    //test : zara0140 / 1234567a
//    @RequestMapping(value="/login", method = {RequestMethod.POST, RequestMethod.GET})
    @PostMapping("/login")
    public ResponseEntity<Object> loginRequest(@RequestBody LoginRequest loginRequest, HttpServletRequest request,  HttpServletResponse response) throws ExecutionException, InterruptedException {
        User user = null;

        Cookie[] cookies = request.getCookies();

        List<Cookie> cookieList = new ArrayList<>();
        if(cookies != null){
            cookieList = Arrays.asList(cookies);

            for (Cookie cookie: cookieList) {
                if(cookie.getName().equals(SESSION_NAME)){
                    HttpSession session = request.getSession();
                    user = (User)session.getAttribute(cookie.getValue());
                    if(user != null && user.getUserId().equals(loginRequest.getId()) && user.getPassword().equals(loginRequest.getPassword())){
                        return ResponseEntity.ok().body(user);
                    }

                }
            }
        }

        user = userService.loginCheck(loginRequest.getId(), loginRequest.getPassword());

        if (user == null) { // 사용자 정보 찾을 수 없음
            return ResponseEntity.badRequest().body("not found user");
        }
//        else if(user.getUserId() == "incorrect password"){ //비밀번호 틀림
//            return ResponseEntity.badRequest().body("incorrect password");
//        }
        else{
            //로그인 성공 처리

            //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
            HttpSession session = request.getSession();
            String sessionId = UUID.randomUUID().toString();
            //세션에 로그인 회원 정보 보관 -> 홈에서 희망업직종이나 희망 지역 받아서 추천해줄 때, 혹은 마이페이지 이동해서 user 정보 db에서 직접 찾지 않아도 세션에서 가져올 수 있음.
            session.setAttribute(sessionId, user);

            Cookie cookie = new Cookie(SESSION_NAME, sessionId);
            response.addCookie(cookie); // 사용자에게 해당 쿠키를 추가

            return ResponseEntity.ok().body(sessionId); //세션아이디 넘기기. (쿠키 넘기기)

        }
    }


    @PostMapping("/signup")
    public ResponseEntity<Object> signupRequest(@RequestBody SignupRequest signupRequest) throws ExecutionException, InterruptedException {
        User user =  userService.addUser(signupRequest);
        if(user == null) return new ResponseEntity<Object>("null exception", HttpStatus.BAD_REQUEST);
        return new ResponseEntity<Object>(user, HttpStatus.CREATED);
    }


    @GetMapping("/signup/verify/id")
    public ResponseEntity<Object> verifyId(@RequestParam String id) throws ExecutionException, InterruptedException{
        String verifyResult = userService.verifyId(id);
        System.out.println(verifyResult);
        if(verifyResult == "null exception") return ResponseEntity.badRequest().body("null exception");
        else return ResponseEntity.ok().body(verifyResult);
    }
    @GetMapping("/signup/verify/nickname")
    public ResponseEntity<Object> verifyNickname(@RequestParam String nickname) throws ExecutionException, InterruptedException{
        String verifyResult = userService.verifyId(nickname);
        if(verifyResult == "null exception") return ResponseEntity.badRequest().body("null exception");
        else return ResponseEntity.ok().body(verifyResult);
    }
    @GetMapping("/signup/verify/email")
    public ResponseEntity<Object> verifyEmail(@RequestParam String email) throws ExecutionException, InterruptedException{
        String verifyResult = userService.verifyId(email);
        if(verifyResult == "null exception") return ResponseEntity.badRequest().body("null exception");
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