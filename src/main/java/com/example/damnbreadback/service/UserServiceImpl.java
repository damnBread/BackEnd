package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.entity.LoginRequest;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserDao userDao;

    @Override
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        return userDao.getUsers();
    }

    // 로그인
    @Override
    public User loginCheck(String id, String pw) throws ExecutionException, InterruptedException {
        return userDao.findUser(id, pw);
    }


    // 회원가입 -> 회원 정보 중복 확인
    @Override
    public String verifyId(String id) throws ExecutionException, InterruptedException {
        System.out.println("-aiaiaiaiaia" + userDao.findId(id).toString());
        if(id == null) return "null exception";
        return userDao.findId(id).toString();
    }

    @Override
    public String verifyNickname(String nickname) throws ExecutionException, InterruptedException {
        if(nickname == null) return "null exception";
        return userDao.findNickname(nickname).toString();
    }

    @Override
    public String verifyEmail(String email) throws ExecutionException, InterruptedException {
        if(email == null) return "null exception";
        return userDao.findEmail(email).toString();
    }


    // 회원가입 -> 신규 회원 저장
    @Override
    public User addUser(SignupRequest signupRequest) throws ExecutionException, InterruptedException {
        if(signupRequest.getId() == null || signupRequest.getBirth() == null ||
                signupRequest.getEmail() == null || signupRequest.getHome() == null ||
                signupRequest.getHopeJob() == null || signupRequest.getNickname() == null ||
                signupRequest.getPw() == null || signupRequest.getPhone() == null ||
                signupRequest.getName() == null || signupRequest.getHopeLocation() == null) {
            return null;
        }

        else {
            User user = new User();
            user.setPassword(signupRequest.getPw());
            user.setName(signupRequest.getName());
            user.setEmail(signupRequest.getEmail());
            user.setNickname(signupRequest.getNickname());
            user.setPhone(signupRequest.getPhone());
            user.setBirth(signupRequest.getBirth());
            user.setGender(signupRequest.isGender());
            user.setHopeJob(signupRequest.getHopeJob());
            user.setHopeLocation(signupRequest.getHopeLocation());
            user.setHome(signupRequest.getHome());

            user.setNoShow(0);
            user.setScore(0);
            user.setCareer(null);
            user.setJoinDate(new Date()); // 가입하는 현재 시간 저장

            HashMap<String, Boolean> isPublicMap = new HashMap<>();
            isPublicMap.put("birth", true);
            isPublicMap.put("career", true);
            isPublicMap.put("email", true);
            isPublicMap.put("gender", true);
            isPublicMap.put("hope-job", true);
            isPublicMap.put("location", true);
            isPublicMap.put("name", true);
            isPublicMap.put("phone", true);
            user.setIsPublic(isPublicMap);

            userDao.insertUser(user);

            return user;
        }

    }

    // 인재정보 -> rank 정보 get
    @Override
    public List<User> getRankScore() throws ExecutionException, InterruptedException {
        List<User> users = userDao.getRankScore();
        return users;
    }

    @Override
    public String getUserId(String id) throws ExecutionException, InterruptedException {
        return userDao.getUserId(id);
    }

    @Override
    public List<String> getBookmarks(String user) throws ExecutionException, InterruptedException {
        return userDao.getScraps(user);
    }


}