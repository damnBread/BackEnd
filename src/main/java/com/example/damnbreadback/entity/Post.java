package com.example.damnbreadback.entity;

import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "post")
public class Post extends BaseTimeEntity{

    @Id
    @GeneratedValue
    private Long postId;

    private String title; // 공고 제목
    private String content; // 공고 내용
    private Long publisher; // 게시자

    private Date deadline; // 마감일

    private String branchName; // 점포명
    private String location; // 근무지역
    private int hourPay; // 시급
    private boolean payMethod; // 임금 지불 방법 (true : 의뢰인 직접 지급 / false : 당일 현장 지급)
    private String job; // 업/직종

//    private LocalDate workDate; // 근무 날짜
//    private int workDay; // 근무 요일
//    private Float workStart; // 근무시작 시간 ex. 4.25 = 4시 25분
//    private Float workEnd; // 근무종료 시간 ex. 10.30 = 10시 30분
    
    private Date workStart; //근무 시작 일시
    private Date workEnd; //근무 종료 일시

    @ColumnDefault("0")
    private int applicantCount; // 지원자 수

    @ColumnDefault("0")
    private int viewCount; // 조회 수

    private boolean genderLimit; //성별 조건 (true : 남자, false : 여자, null : 무관)
    private int ageMax; // 나이 조건 (최대나이 / -1 : 무관 )
    private int ageMin; // 나이 조건 (최소나이 / -1 : 무관 )
    private int careerLimit; // 해당 업직종에 대한 경력 조건 ( -1 : 무관 / 0 : 신입 )


    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> histories;

    @OneToMany(mappedBy = "post")
    private Set<Scrap> scrap;


    public Post(PostDto dto) throws CloneNotSupportedException {
//        this.title = dto.getTitle();
//        this.content = dto.getContent();
//        this.deadline = dto.getDeadline();
//        this.branchName = dto.getBranchName();
//        this.location = dto.getLocation();
//        this.hourPay = dto.getHourPay();
//        this.payMethod = dto.isPayMethod();
//        this.job = dto.getJob();
    }
}