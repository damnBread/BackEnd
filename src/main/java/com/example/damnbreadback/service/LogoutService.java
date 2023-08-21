package com.example.damnbreadback.service;

import com.example.damnbreadback.repository.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final TokenRepository tokenRepository;

    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {

        final String accessHeader = request.getHeader("Authorization");
        final String refreshHeader = request.getHeader("RefreshToken");
        final String accessToken;
        final String refreshToken;
        if (accessHeader == null || !accessHeader.startsWith("Bearer ")) {
            return;
        }
        if (refreshHeader == null || !refreshHeader.startsWith("Bearer ")) {
            return;
        }
        accessToken = accessHeader.substring(7);
        refreshToken = refreshHeader.substring(7);
        tokenRepository.deleteByRefreshToken(refreshToken);
//        if (storedToken != null) {
//            storedToken.setExpired(true);
//            storedToken.setRevoked(true);
//            tokenRepository.save(storedToken);
//            SecurityContextHolder.clearContext();
//        }
    }
}