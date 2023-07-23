package com.example.damnbreadback.sessionManager;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    public static final String SESSION_COOKIE_NAME = "loginUser";
    private Map<String, Object> sessionStore = new ConcurrentHashMap<>();

    //## 세션 생성 ##//
    public String  createSession(Object value, HttpServletResponse response) {
        //세션 id(UUID)를 생성하고, 값을 세션에 저장
        String sessionId = UUID.randomUUID().toString();
        sessionStore.put(sessionId, value);

        //쿠키 생성
        Cookie mySessionCookie = new Cookie(SESSION_COOKIE_NAME, sessionId);
        response.addCookie(mySessionCookie);

        return sessionId;
    }

    //## 세션 조회 ##//
    public Object getSession(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie == null) {
            return null;
        }
        return sessionStore.get(sessionCookie.getValue());
    }

    //## 세션 만료 ##//
    public void expire(HttpServletRequest request) {
        Cookie sessionCookie = findCookie(request, SESSION_COOKIE_NAME);
        if (sessionCookie != null) {
            sessionStore.remove(sessionCookie.getValue());
        }
    }

    // 서블릿에서 세션 객체(Session)를 제공해주긴 하지만, 이해를 돕기위해 직접 구현해보았다.
    public Cookie findCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() == null) { // 쿠키값은 array 또는 null 로 반환된다.
            return null;
        }
        return Arrays.stream(request.getCookies()) // 해당 쿠키이름을 찾아 반환하는 로직
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findAny() // 처음으로 발견된 값 아무거나
                .orElse(null); // 없다면 null 반환
    }

}