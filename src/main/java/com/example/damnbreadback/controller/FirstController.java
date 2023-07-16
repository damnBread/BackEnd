package com.example.damnbreadback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
public class FirstController {

    @GetMapping("/")
    public List<String> test() {
        System.out.println("ㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱㄱ");
        return Arrays.asList("인사", "첫번재");
    }

    @GetMapping("/showMe")
    public List<String> hello() {
        System.out.println("sssssssssssss");
        return Arrays.asList("인사", "두번째");
    }
}
