package com.example.damnbreadback.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;
import java.util.Date;
import java.util.List;

public class JwtUtils {
    // 어세스 토큰 유효시간 | 20s
    private static long accessTokenValidTime = 1000 * 60 * 60L; //1시간
    // 리프레시 토큰 유효시간 | 1m
    private static long refreshTokenValidTime = 1000 * 60 * 60L * 24 * 14; //2주

    // This method extracts the userId from the JWT token
    public static String getUserIdFromToken(String token, String secretKey) throws AccessDeniedException {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getSubject();
    }

    // This method extracts the role from the JWT token
    public static String getRoleFromToken(String token, String secretKey) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        return (String) claims.get("role");
    }

    // This method validates the JWT token and returns the user details if the token is valid
    public static UserDetails validateTokenAndGetUserDetails(String token, String secretKey) {
        try {
            // Parse the token and extract claims
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();

            // Check if the token is expired
            if (claims.getExpiration().before(new Date())) {
                // Token is expired
                return null;
            }

            // Get user details from claims
            String userId = claims.getSubject();
            String role = (String) claims.get("role"); // Assuming you've stored the role as a custom claim in the token

            // Create and return UserDetails object with the user details
            return new UserPrincipal(userId, role);
        } catch (Exception e) {
            // Token validation failed (invalid token, signature, etc.)
            return null;
        }
    }



    public static String createAccessToken(String userId, String role, String secretKey) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role) // Adding the "role" claim to include the user's role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public static String createRefreshToken(String userId, String role, String secretKey) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role) // Adding the "role" claim to include the user's role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + refreshTokenValidTime))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    // 어세스 토큰 헤더 설정
    public static void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", "Bearer "+ accessToken);
    }

    // 리프레시 토큰 헤더 설정
    public static void setHeaderRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("RefreshToken", "Bearer "+ refreshToken);
    }

    // Request의 Header에서 AccessToken 값을 가져옵니다. "authorization" : "token'
    public static String resolveAccessToken(HttpServletRequest request) {
        if(request.getHeader("Authorization") != null )
            return request.getHeader("Authorization").substring(7);
        return null;
    }
    // Request의 Header에서 RefreshToken 값을 가져옵니다. "authorization" : "token'
    public static String resolveRefreshToken(HttpServletRequest request) {
        if(request.getHeader("RefreshToken") != null )
            return request.getHeader("RefreshToken").substring(7);
        return null;
    }


}
