package com.example.damnbreadback.service;

import com.example.damnbreadback.config.JwtUtils;
import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.dto.TokenDTO;
import com.example.damnbreadback.entity.RefreshToken;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.TokenRepository;
import com.example.damnbreadback.repository.UserRepository;
import com.example.damnbreadback.repository.UserSpecification;
import com.google.api.client.auth.oauth2.TokenResponse;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    @Value("${JWT.SECRET}")
    private String secretKey;

    @Autowired
    TokenRepository tokenRepository;


    @Override
    public RefreshToken addToken(String token) {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setRefreshToken(token);

        tokenRepository.save(refreshToken);

        return refreshToken;
    }

    @Override
    @Transactional
    public TokenDTO tokenValidIssue(String accessToken, String refreshToken) throws AccessDeniedException, ExecutionException, InterruptedException {
        System.out.println("Access 토큰 만료됨");
        TokenDTO tokenDto = new TokenDTO();
        if(JwtUtils.validateTokenAndGetUserDetails(refreshToken, secretKey) != null){     //들어온 Refresh 토큰이 자체적으로 유효한지
            System.out.println("Refresh 토큰은 유효함");
            String userId = JwtUtils.getUserIdFromToken(refreshToken, secretKey);

            if(tokenRepository.existsByRefreshToken(refreshToken)) {   //DB의 원본 refresh토큰과 지금들어온 토큰이 같은지 확인
                System.out.println("Access 토큰 재발급 완료");
                tokenDto = new TokenDTO();
                String newAcessToken = JwtUtils.createAccessToken(userId, "USER", secretKey);
                String newRefreshToken = JwtUtils.createRefreshToken(userId, "USER", secretKey);
                tokenDto.setAcessToken(newAcessToken);
                tokenDto.setRefreshToken(newRefreshToken);

                tokenRepository.deleteByRefreshToken(refreshToken);

                RefreshToken saveNewRefreshToken = new RefreshToken();
                saveNewRefreshToken.setRefreshToken(newRefreshToken);
                tokenRepository.save(saveNewRefreshToken);
            }
            else{
                //DB의 Refresh토큰과 들어온 Refresh토큰이 다르면 중간에 변조된 것임
                //예외발생
                tokenDto.setAcessToken("fail");
                tokenDto.setRefreshToken("fail");
                System.out.println("Refresh Token Tampered");
            }
        }
        else{
            //입력으로 들어온 Refresh 토큰이 유효하지 않음
        }

        return tokenDto;
    }
}