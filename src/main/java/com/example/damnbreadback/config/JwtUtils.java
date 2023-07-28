package com.example.damnbreadback.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.file.AccessDeniedException;
import java.util.Date;

public class JwtUtils {

    public static String getUserName(String token, String secretKey) throws AccessDeniedException {
        verify(token, secretKey);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userId", String.class);
    }

    public static boolean isExpired(String token, String secretKey) throws AccessDeniedException {
        verify(token, secretKey);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static void verify(String token, String secretKey) throws AccessDeniedException {
        try{
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
        }catch (Exception e){
            System.out.println("access Denied handler");
        }
    }

    public static String createJwt(String id, String secretKey, Long expiredMs){
        Claims claims = Jwts.claims(); // Jwt에서 제공하는 claims라는 map형식의 타입 사용해서 토큰쌍들 저장
        claims.put("userId", id);


        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
