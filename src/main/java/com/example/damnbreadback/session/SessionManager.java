package com.example.damnbreadback.session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SessionManager {
    HttpServletRequest request;
    HttpServletResponse response;
//    String SESSION_NAME;

    public SessionManager(HttpServletRequest request) {
        this.request = request;
    }

    public SessionManager(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public HttpSession getSession(){
        return request.getSession();
    }

    public List<Cookie> getCookies(){
        Cookie[] cookies = request.getCookies();

        return Arrays.asList(cookies);
    }

    public Cookie getCookie(String sessionName){
        List<Cookie> cookieList = getCookies();

        if(cookieList != null){
            for (Cookie cookie: cookieList) {
                if(cookie.getName().equals(sessionName)){
                    return cookie;
                }
            }
        }
        return null;
    }

    public Object getSessionValue(String sessionName){
        HttpSession session = getSession();

        Cookie cookie = getCookie(sessionName);
        if(cookie == null) return null;

        return session.getAttribute(cookie.getValue());
    }

    public String createSession(String sessionName, Object object){
        //세션에 저장
        HttpSession session = getSession();
        String sessionId = UUID.randomUUID().toString();
        //세션에 로그인 회원 정보 보관 -> 홈에서 희망업직종이나 희망 지역 받아서 추천해줄 때, 혹은 마이페이지 이동해서 user 정보 db에서 직접 찾지 않아도 세션에서 가져올 수 있음.
        session.setAttribute(sessionId, object);

        //쿠키에 저장
        Cookie cookie = new Cookie(sessionName, sessionId);
        response.addCookie(cookie); // 사용자에게 해당 쿠키를 추가

        return sessionId;
    }
}
