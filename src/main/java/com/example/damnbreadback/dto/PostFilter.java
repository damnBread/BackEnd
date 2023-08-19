package com.example.damnbreadback.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostFilter {
    private List<String> location;
    private List<String> job;
    private List<Integer> dayOfWeek;

    // 0 - 오전(07-12)
    // 1 - 오후 (12-17)
    // 2 - 저녁 (17-24)
    // 3 - 새벽 (00-07)
    private List<Integer> workTime;

    // true - 남자, false - 여자
    private Boolean genderLimit;
    // true - 무관제외, false - 무관포함
    private Boolean isFree;

    private int minAge = -1;
    private int hourPay = -1;

    // true - 의뢰인직접지급, false - 현장지급
    private Boolean payMethod;

}
