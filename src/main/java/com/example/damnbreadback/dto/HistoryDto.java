package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.User;
import lombok.*;

import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class HistoryDto implements Cloneable {
    private Long id; // 고유 아이디

   private Long user_id;
   private Long post_id;

   private int status_code;

    public static HistoryDto toDTO(History entity){
        return HistoryDto.builder()
                .id(entity.getHistoryId())
//                .user_id(entity.getUser().getUserId())
//                .post_id(entity.getPost().getPostId())
                .status_code(entity.getStatusCode())
                .build();

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();    // 얕은 사본을 반환
    }
}
