package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.Career;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UserDTO {
    private Long userId; // 기본키
    public Timestamp timestamp;
    private String name; // 이름
    private String nickname; // 닉네임
    private String id; // 아이디
    private String pw; // 비밀번호
    private String email; // 이메일
    private String phone; // 전화번호
    private String home; // 거주지 -> string 으로 (private String location)
    private Date birth; // 생년월일
    private boolean gender; // 성별 (true : 남자 , false : 여자)
    private String introduce; //소개글
    private String badge; //뱃지 ex. "00000000", "00010000"
    private int noShow; // 노쇼 횟수
    private int score; // 점수
    private String hopeJob; // 희망 업/직종
    private String hopeLocation; // 희망 근무지역 -> String Array
    private String isPublic; // 정보 공개 여부 "0101011" < 이런 식으로 이진값을 받아야할듯
//    private Set<Scrap> scraps; // 스크랩한 포스트 목록
//    private List<Career> career; // 경력
//    private List<History> histories; // 땜빵이력

    public static UserDTO toDTO(User entity){
        return UserDTO.builder()
                .userId(entity.getUserId())
                .timestamp(entity.getTimestamp())
                .name(entity.getName())
                .nickname(entity.getNickname())
                .id(entity.getId())
                .pw(entity.getPw())
                .email(entity.getEmail())
                .phone(entity.getPhone())
                .home(entity.getHome())
                .birth(entity.getBirth())
                .gender(entity.isGender())
                .introduce(entity.getIntroduce())
                .badge(entity.getBadge())
                .noShow(entity.getNoShow())
                .score(entity.getScore())
                .hopeJob(entity.getHopeJob())
                .hopeLocation(entity.getHopeLocation())
                .isPublic(entity.getIsPublic())
//                .scraps(entity.getScraps())
//                .career(entity.getCareer())
//                .histories(entity.getHistories())
                .build();
                
    }
}
