package com.example.damnbreadback.config;

import com.example.damnbreadback.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;

    @Value("${JWT.SECRET}")
    private final String secretKey;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Token에서 Username 꺼내기
        final String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String authorization = request.getHeader("Authorization");
        System.out.println("authorization : {"+ authorization+"}");

        //token안보내면 권한 없음.
        if(authorization == null || authorization.startsWith("{Bearer ")) {
            System.out.println("잘못된 authorization 입니다.");

            filterChain.doFilter(request, response);
            return;
        }
        String token = authorization.split(" ")[1];

        //Token Expired 체크
        if(JwtUtils.isExpired(token, secretKey)){
            System.out.println("3??authorization : {"+ authorization+"}");
            System.out.println("토큰이 만료되었습니다.");
            filterChain.doFilter(request, response);
            return;
        }

        String userId = JwtUtils.getUserName(token, secretKey);


        //권한부여
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("USER")));

        System.out.println(authenticationToken);

        //Detail을 넣어줌
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println("3 authorization : {"+ authorization+"}");
        filterChain.doFilter(request, response);
    }
}