package com.example.damnbreadback.service;

import com.example.damnbreadback.repository.UserRepository;
import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.config.JwtUtils;
import com.example.damnbreadback.dto.UserFilter;
import com.example.damnbreadback.repository.UserSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${JWT.SECRET}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;
    @Autowired
    private final UserDao userDao;

    @Autowired
    UserRepository userRepository;

    // 로그인 ===============================================================================
    public String login(String id, String pw) throws ExecutionException, InterruptedException{
        //인증과정 생략
        User user = loginCheck(id, pw);

        if(user== null) return null;

        if (user.getId().equals("fail to find user")) return "fail to find user";
        else if(user.getId().equals("db null exception")) return "db null exception";
//        else return JwtUtils.createJwt(id, secretKey, expiredMs);
        else return JwtUtils.generateToken(id, "USER", secretKey, expiredMs);

    }

    // 로그인
    @Override
    public User loginCheck(String id, String pw){
        User user = userRepository.findUserById(id);
        System.out.println(user);
        if(user == null) return null;

        if(user.getPw().equals(pw)) return user;
        else return null;
//        return userDao.findUser(id, pw);
//        return null;
    }

    //=========================================================================================

    // 회원가입 ==============================================================================
    // 회원가입 -> 회원 정보 중복 확인
    @Override
    public String verifyId(String id) throws ExecutionException, InterruptedException {
        if(id == null) return "null exception";
        User user = userRepository.findUserById(id);
        if(user == null) return "false";
        else return "true";
//        return userDao.findId(id).toString();
//        return null;
    }

    @Override
    public String verifyNickname(String nickname) throws ExecutionException, InterruptedException {
        if(nickname == null) return "null exception";
        User user = userRepository.findUserByNickname(nickname);
        if(user == null) return "false";
        else return "true";
//        return userDao.findNickname(nickname).toString();
//        return null;
    }

    @Override
    public String verifyEmail(String email) throws ExecutionException, InterruptedException {
        if(email == null) return "null exception";
        User user = userRepository.findUserByEmail(email);
        if(user == null) return "false";
        else return "true";
//        return userDao.findEmail(email).toString();
//        return null;
    }


    // 회원가입 -> 신규 회원 저장
    @Override
    public User addUser(User user) {
        if(user.getId() == null || user.getBirth() == null ||
                user.getEmail() == null || user.getHome() == null ||
                user.getHopeJob() == null || user.getNickname() == null ||
                user.getPw() == null || user.getPhone() == null ||
                user.getName() == null || user.getHopeLocation() == null) {
            return null;
        }

//        Authority authority = Authority.builder()
//                .authorityName("ROLE_USER")
//                .build();
//      userDao.insertUser(user);
        userRepository.save(user);

        return user;
    }

    // ====================================================================================================


    //기본키 userId로 user 찾기.
    @Override
    public User getUserById(Long id)  throws ExecutionException, InterruptedException {
        return userRepository.findById(String.valueOf(id)).get();
//        return userDao.getUserById(id);
//        return null;
    }

    // 인재정보 -> rank 정보 get
    @Override
    public List<User> getRankScore(int page){
        PageRequest pageRequest = PageRequest.of(page, 20);
//        return userRepository.findAll(pageRequest);

        return userRepository.findUsersByOrderByScoreDesc(pageRequest);
//        return userDao.getRankScore(page);
//        return null;
    }

    @Override
    public Page getRankFilter(UserFilter userFilter, int page){
        PageRequest pageRequest = PageRequest.of(page, 20);

        List<String> location = Arrays.asList(userFilter.getLocation().split("\\|"));


        List<String> job = Arrays.asList(userFilter.getJob().split("\\|"));


        List<Boolean> gender = new ArrayList<Boolean>();
        if(userFilter.getGender().get(0) != 0) gender.add(true);
        if(userFilter.getGender().get(1) != 0) gender.add(false);

        Date birth = calculateBirthDateFromAge(userFilter.getAge());
        System.out.println(birth);

        Specification<User> spec = (root, query, criteriaBuilder) -> null;

        if(location != null)
            spec = spec.and(UserSpecification.hasLocation(location));
        if(job != null)
            spec = spec.and(UserSpecification.hasJob(job));
        if (gender != null)
            spec = spec.and(UserSpecification.isGender(gender));
        if (birth != null)
            spec = spec.and(UserSpecification.overAge(birth));

        return userRepository.findAll(spec, pageRequest);

//        return userDao.getRankFilter(location, job, gender, birth, page);
    }

    public Date calculateBirthDateFromAge(int age) {
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDate = calendar.get(Calendar.DATE);
        int birthYear = currentYear - age;

        // Assuming birth date on January 1st of the calculated birth year
        calendar.set(birthYear, currentMonth, currentDate);
        return calendar.getTime();
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

    @Override
    public Long findUserIdById(String id) throws ExecutionException, InterruptedException {
        return userRepository.findUserById(id).getUserId();
    }


    @Override
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        return userDao.getAllUsers();
//        return null;
    }

}