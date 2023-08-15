package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);

    @Modifying
    @Query(value = "DELETE FROM refresh_token t WHERE t.refresh_token = :token",
            nativeQuery = true)
    int deleteByRefreshToken(@Param("token") String token);

}