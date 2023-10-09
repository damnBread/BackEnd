package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PostDto implements Cloneable {
    private Long postId;

    private String branchName; // 점포명
    private String location; // 근무지역
    private int hourPay; // 시급
    private boolean payMethod; // 임금 지불 방법 (true : 의뢰인 직접 지급 / false : 당일 현장 지급)
    private String job; // 업/직종

    private Date workStart; // 근무 시작 일시
    private Date workEnd; // 근무 종료 일시

    private int applicantCount; // 지원자 수
    private int viewCount; // 조회 수

    private String title; // 공고 제목
    private String content; // 공고 내용

    private Long publisher; // 게시자
    private Date deadline; // 마감일

    private boolean genderLimit; //성별 조건 (true : 남자, false : 여자)
    private int ageMax; // 나이 조건 (최대나이)
    private int ageMin; // 나이 조건 (최소나이)
    private int careerLimit; // 해당 업직종에 대한 경력 조건
    private int recruitNumber; // 모집인원
    private String additionalLimit; // 우대사항


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();    // 얕은 사본을 반환
    }

    public static PostDto toDTO(Post entity){
        try{
            return PostDto.builder()
                    .postId(entity.getPostId())
                    .branchName(entity.getBranchName())
                    .location(entity.getLocation())
                    .hourPay(entity.getHourPay())
                    .payMethod(entity.isPayMethod())
                    .job(entity.getJob())
                    .workStart(entity.getWorkStart())
                    .workEnd(entity.getWorkEnd())
                    .applicantCount(entity.getApplicantCount())
                    .viewCount(entity.getViewCount())
                    .title(entity.getTitle())
                    .content(entity.getContent())
                    .publisher(entity.getPublisher())
                    .deadline(entity.getDeadline())
                    .genderLimit(entity.isGenderLimit())
                    .ageMax(entity.getAgeMax())
                    .ageMin(entity.getAgeMin())
                    .careerLimit(entity.getCareerLimit())
                    .build();
        }catch (Error e){
            return null;
        }


    }
}
