package com.example.damnbreadback.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.file.AccessDeniedException;
import java.util.Date;

public class JwtUtils {

    // This method extracts the userId from the JWT token
    public static String getUserIdFromToken(String token, String secretKey) throws AccessDeniedException {
        verify(token, secretKey);
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

    public static boolean isExpired(String token, String secretKey) throws AccessDeniedException {
        verify(token, secretKey);
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }

    public static void verify(String token, String secretKey) throws AccessDeniedException {
        Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
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

//        return Jwts.builder()
//                .claim("userId", "USER")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
//                .signWith(SignatureAlgorithm.HS512, secretKey)
//                .compact();
    }

    public static String generateToken(String userId, String role, String secretKey,  Long expiredMs) {
        return Jwts.builder()
                .setSubject(userId)
                .claim("role", role) // Adding the "role" claim to include the user's role
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}
