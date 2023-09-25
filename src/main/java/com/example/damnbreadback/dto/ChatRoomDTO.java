package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class ChatRoomDTO {
    private Long id;

    private Long user1_id;
    private Long user2_id;
}
