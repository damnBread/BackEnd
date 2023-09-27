package com.example.damnbreadback.entity;

import com.example.damnbreadback.dto.UserDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long userId; // 기본키

    @Column(name = "join_date")
    @CreationTimestamp //this adds the default timestamp on save
    public Timestamp timestamp;

    @Column(name = "name")
    private String name; // 이름

    @Column(name = "nickname")
    private String nickname; // 닉네임

    @Column(name = "id")
    private String id; // 아이디

    @Column(name = "pw")
    private String pw; // 비밀번호

    @Column(name = "email")
    private String email; // 이메일

    @Column(name = "phone")
    private String phone; // 전화번호

    @Column(name = "home")
    private String home; // 거주지 -> string 으로 (private String location)

    @Column(name = "birth")
    private Date birth; // 생년월일

    @Column(name = "gender")
    private boolean gender; // 성별 (true : 남자 , false : 여자)

    @ColumnDefault("'introduce'")
    @Column(name = "introduce")
    private String introduce; //소개글

    @ColumnDefault("00000000")
    @Column(name = "badge")
    private String badge; //뱃지 ex. "00000000", "00010000"



    @ColumnDefault("0")
    @Column(name = "score")
    private int score; // 점수


    @Column(name = "hope_job")
    private String hopeJob; // 희망 업/직종

    @Column(name = "hope_location")
    private String hopeLocation; // 희망 근무지역 -> String Array

//    @Column(name = "is_public", columnDefinition = "VARCHAR(7) DEFAULT '0000000'")
//    private String isPublic;

    @ColumnDefault("0000000")
    @Column(name = "is_public")
    private String isPublic; // 정보 공개 여부 "0101011" < 이런 식으로 이진값을 받아야할듯

    @OneToMany(mappedBy = "user")
    private List<Noshow> noShow; // 노쇼

    @OneToMany(mappedBy = "user")
    private Set<Scrap> scraps; // 스크랩한 포스트 목록

    @OneToMany(fetch=FetchType.EAGER)
    private List<Career> career; // 경력

    @OneToMany(fetch = FetchType.EAGER)
    private List<History> histories; // 땜빵이력

    public static User toEntity(UserDTO dto){
        return User.builder()
                .userId(dto.getUserId())
                .timestamp(dto.getTimestamp())
                .name(dto.getName())
                .nickname(dto.getNickname())
                .id(dto.getId())
                .pw(dto.getPw())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .home(dto.getHome())
                .birth(dto.getBirth())
                .gender(dto.isGender())
                .introduce(dto.getIntroduce())
                .badge(dto.getBadge())
                .score(dto.getScore())
                .hopeJob(dto.getHopeJob())
                .hopeLocation(dto.getHopeLocation())
                .isPublic(dto.getIsPublic())
//                .scraps(dto.getScraps())
//                .career(dto.getCareer())
//                .histories(dto.getHistories())
                .build();

    }

    public void changeName(String name){
        this.name = name;
    }
}