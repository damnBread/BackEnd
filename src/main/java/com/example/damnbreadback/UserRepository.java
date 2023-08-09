package com.example.damnbreadback;

import com.example.damnbreadback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findUserById(String id);
    User findUserByNickname(String nickname);
    User findUserByEmail(String email);

    @Query(value = "SELECT FROM User u ORDER BY u.score DESC, CASE WHEN u.score = (SELECT MAX(u2.score) FROM User u2) THEN u.joinDate END DESC",
            nativeQuery = true)
    List<User> findUsersByOOrderByScoreDesc();


}
