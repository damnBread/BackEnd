package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.RefreshToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);


    @Modifying
    @Transactional
    @Query("DELETE FROM RefreshToken t WHERE t.refreshToken = :token")
    void deleteByRefreshToken(@Param("token") String token);

    @Query(value = "SELECT * FROM RefreshToken t WHERE t.refresh_token = :token",
            nativeQuery = true)
    String findByRefreshToken(@Param("token") String token);

}