package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.service.UserService;
import com.example.damnbreadback.sessionManager.SessionManager;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/exam/svc/v1")
public class UserController {
    public static final String SESSION_NAME = "loginUser";

    private SessionManager sessionManager = new SessionManager();
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

                    System.out.println(session.getAttributeNames());

                    user = (User)session.getAttribute(SESSION_NAME);
                    return ResponseEntity.ok().body(user);
                }
            }
        }

        System.out.println(loginRequest.getId() +""+loginRequest.getPassword());
        user = userService.loginCheck(loginRequest.getId(), loginRequest.getPassword());

        if (user == null) { // 사용자 정보 찾을 수 없음
            return ResponseEntity.badRequest().body("not found user");
        }
        else if(user.getId() == "incorrect password"){ //비밀번호 틀림
            return ResponseEntity.badRequest().body("incorrect password");
        }
        else{
            //로그인 성공 처리
            //sessionManager 사용 ?? -------
            // -----------------------------------------------
            String sessionId = sessionManager.createSession(user, response);
//            return ResponseEntity.ok().body(sessionId); // 세션 아이디 전달.
            //----------------------------------------------------------------------------

            // ----------------------------------------------------------------------------
            //세션이 있으면 있는 세션 반환, 없으면 신규 세션을 생성
//            HttpSession session = request.getSession();
//            //세션에 로그인 회원 정보 보관 -> 홈에서 희망업직종이나 희망 지역 받아서 추천해줄 때, 혹은 마이페이지 이동해서 user 정보 db에서 직접 찾지 않아도 세션에서 가져올 수 있음.
//            session.setAttribute(SESSION_NAME, user);
//            System.out.println(session);
//
//            // 세션키가 담긴 쿠키를 클라이언트에게 전달하는 방식---------------------------------
            Cookie cookie = new Cookie(SESSION_NAME, sessionId);
            cookie.setMaxAge(60*60*24*365);			//해당 쿠키의 유효시간을 설정 (초 기준)
            response.addCookie(cookie); // 사용자에게 해당 쿠키를 추가
//
//            System.out.println(cookie.getName());
            return ResponseEntity.ok().body(cookie); //세션아이디 넘기기. (쿠키 넘기기)
            //----------------------------------------------------------------------------

            // 세션키만 String값으로 클라이언트에게 전달하는 방식---------------------------------
//            HttpSession session = request.getSession();
//            return ResponseEntity.ok().body(session.getId()); //세션아이디 넘기기. (쿠키 넘기기)
            //----------------------------------------------------------------------------
        }
    }


    @PostMapping("/signup")
    public ResponseEntity<User> signupRequest(@RequestBody SignupRequest signupRequest) throws ExecutionException, InterruptedException {
        User user =  userService.addUser(signupRequest);
        return new ResponseEntity<User>(user, HttpStatus.CREATED);
    }


    @GetMapping("/signup/verify/id")
    public ResponseEntity<Object> verifyId(@RequestParam String id) throws ExecutionException, InterruptedException{
        Boolean verifyResult = userService.verifyId(id);
        return ResponseEntity.ok().body(verifyResult);
    }
    @GetMapping("/signup/verify/nickname")
    public ResponseEntity<Object> verifyNickname(@RequestParam String nickname) throws ExecutionException, InterruptedException{
        Boolean verifyResult = userService.verifyId(nickname);
        return ResponseEntity.ok().body(verifyResult);
    }
    @GetMapping("/signup/verify/email")
    public ResponseEntity<Object> verifyEmail(@RequestParam String email) throws ExecutionException, InterruptedException{
        Boolean verifyResult = userService.verifyId(email);
        return ResponseEntity.ok().body(verifyResult);
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