package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<RefreshToken, Long> {
    boolean existsByRefreshToken(String token);

    Integer deleteRefreshTokenByRefreshToken(String RefreshToken);
}