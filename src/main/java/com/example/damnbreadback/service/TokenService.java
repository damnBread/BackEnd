package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.TokenDTO;
import com.example.damnbreadback.entity.RefreshToken;
import com.example.damnbreadback.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public interface TokenService {
    RefreshToken addToken(RefreshToken refreshTokenEntity) throws ExecutionException, InterruptedException ;
    TokenDTO tokenValidIssue(String accessToken, String refreshToken) throws AccessDeniedException, ExecutionException, InterruptedException;
}
