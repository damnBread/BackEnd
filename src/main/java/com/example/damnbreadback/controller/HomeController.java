package com.example.damnbreadback.controller;

import com.example.damnbreadback.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Member;
import java.util.Arrays;
import java.util.List;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return "home";
        }

        // 세션에 저장된 회원 조회
        User loginUser = (User) session.getAttribute("loginUser");

        // 세션에 회원 데이터가 없으면 홈으로 이동
        if (loginUser == null) {
            return "home";
        }

        // 세션이 유지되면 로그인으로 이동
        model.addAttribute("loginUser", loginUser);

        System.out.println(model.getAttribute("loginUser"));

        return "loginHome";
    }

    @GetMapping("/showMe")
    public List<String> hello() {
        System.out.println("sssssssssssss");
        return Arrays.asList("인사", "두번째");
    }
}
