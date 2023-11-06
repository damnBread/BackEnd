package com.example.damnbreadback.dto;

import com.example.damnbreadback.entity.Chatroom;
import com.example.damnbreadback.entity.Comment;
import com.example.damnbreadback.entity.User;
import jakarta.persistence.Entity;
import lombok.*;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ChatRoomDTO {
    private Long id;

    private Long user_appliance_id;
    private Long user_publisher_id;

    public static ChatRoomDTO toDTO(Chatroom entity) {
        try {
            return ChatRoomDTO.builder()
                    .id(entity.getRoomId())
                    .user_appliance_id(entity.getUser1().getUserId())
                    .user_publisher_id(entity.getUser2().getUserId())
                    .build();
        } catch (Error e) {
            return null;
        }
    }
}
