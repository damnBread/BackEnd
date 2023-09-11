package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.ChatMessage;
import com.example.damnbreadback.entity.Chatroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chatroom, Long> {

//    private Map<String, ChatRoomDTO> chatRoomMap;
//
//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }
//
//    public List<ChatRoomDTO> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
//
//    public ChatRoomDTO findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }

//    List<Chat> findByUser1OrUser2(String userId);

    @Query(value = "SELECT * FROM chatroom c WHERE c.user1 = :userId OR c.user2 = :userId",
        nativeQuery = true)
    List<Chatroom> getChatrooms(@Param("userId") String userId);


    Chatroom getChatroomByRoomId(String roomId);
}