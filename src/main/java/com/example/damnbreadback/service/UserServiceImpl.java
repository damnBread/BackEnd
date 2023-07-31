package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.entity.SignupRequest;
import com.example.damnbreadback.config.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${JWT.SECRET}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;
    @Autowired
    private final UserDao userDao;

    public String login(String id, String pw) throws ExecutionException, InterruptedException{
        //인증과정 생략
        User user = loginCheck(id, pw);
        if(user== null) return null;

        if (user.getId().equals("fail to find user")) return "fail to find user";
        else if(user.getId().equals("db null exception")) return "db null exception";
        else return JwtUtils.createJwt(id, secretKey, expiredMs);

    }

    @Override
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        return userDao.getAllUsers();
//        return null;
    }

    @Override
    public User getUserByUserId(String id)  throws ExecutionException, InterruptedException {
        return userDao.getUserById(id);
//        return null;
    }

    // 로그인
    @Override
    public User loginCheck(String id, String pw) throws ExecutionException, InterruptedException {
        return userDao.findUser(id, pw);
//        return null;
    }


    // 회원가입 -> 회원 정보 중복 확인
    @Override
    public String verifyId(String id) throws ExecutionException, InterruptedException {
        if(id == null) return "null exception";
        return userDao.findId(id).toString();
//        return null;
    }

    @Override
    public String verifyNickname(String nickname) throws ExecutionException, InterruptedException {
        if(nickname == null) return "null exception";
        return userDao.findNickname(nickname).toString();
//        return null;
    }

    @Override
    public String verifyEmail(String email) throws ExecutionException, InterruptedException {
        if(email == null) return "null exception";
        return userDao.findEmail(email).toString();
//        return null;
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
            user.setId(signupRequest.getId());
            user.setPw(signupRequest.getPw());
            user.setName(signupRequest.getName());
            user.setEmail(signupRequest.getEmail());
            user.setNickname(signupRequest.getNickname());
            user.setPhone(signupRequest.getPhone());
            user.setBirth(signupRequest.getBirth());
            user.setGender(signupRequest.isGender());
            user.setHome(signupRequest.getHome());
            user.setHopeJob(signupRequest.getHopeJob());
            user.setHopeLocation(signupRequest.getHopeLocation());

            user.setNoShow(0);
            user.setScore(0);
            user.setJoinDate(new Date()); // 가입하는 현재 시간 저장
            user.setIsPublic("0000000");

            HashMap<String, Boolean> isPublicMap = new HashMap<>();
            isPublicMap.put("birth", true);
            isPublicMap.put("career", true);
            isPublicMap.put("email", true);
            isPublicMap.put("gender", true);
            isPublicMap.put("hope-job", true);
            isPublicMap.put("location", true);
            isPublicMap.put("name", true);
            isPublicMap.put("phone", true);

            userDao.insertUser(user);

            return user;
        }

    }

    // 인재정보 -> rank 정보 get
    @Override
    public List<User> getRankScore() throws ExecutionException, InterruptedException {
        //List<User> users = userDao.getRankScore();
        return null;
    }

    @Override
    public String getUserId(String id) throws ExecutionException, InterruptedException {
        //return userDao.getUserId(id);
        return null;
    }

    @Override
    public List<String> getBookmarks(String user) throws ExecutionException, InterruptedException {
        //return userDao.getScraps(user);
        return null;
    }


}