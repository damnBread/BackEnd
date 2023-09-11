package com.example.damnbreadback.service;

import com.example.damnbreadback.config.JwtUtils;
import com.example.damnbreadback.entity.RefreshToken;
import com.example.damnbreadback.repository.TokenRepository;
import com.example.damnbreadback.repository.UserRepository;
import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.dto.UserFilter;
import com.example.damnbreadback.repository.UserSpecification;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    private static long accessTokenValidTime = 1000 * 60 * 60L; //1시간
    // 리프레시 토큰 유효시간 | 1m
    private static long refreshTokenValidTime = 1000 * 60 * 60L * 24 * 14; //2주
    @Value("${JWT.SECRET}")
    private String secretKey;

    private final UserDao userDao;

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenService tokenService;

    // 로그인 ===============================================================================
    public String login(String id, String pw, HttpServletResponse response) throws ExecutionException, InterruptedException{
        //인증과정 생략
        User user = loginCheck(id, pw);

        if(user== null) return null;

        if (user.getId().equals("fail to find user")) return "fail to find user";
        else if(user.getId().equals("db null exception")) return "db null exception";
//        else return JwtUtils.createJwt(id, secretKey, expiredMs);
        else{
            String accessToken = JwtUtils.createAccessToken(id, "USER", secretKey);
            String refreshToken = JwtUtils.createRefreshToken(id, "USER", secretKey);

            RefreshToken refreshTokenEntity = new RefreshToken();
            refreshTokenEntity.setRefreshToken(refreshToken);
            refreshTokenEntity.setAccessToken(accessToken);

            tokenService.addToken(refreshTokenEntity);

            JwtUtils.setHeaderAccessToken(response, accessToken);
            JwtUtils.setHeaderRefreshToken(response, refreshToken);

            return accessToken;

        }

    }

    public String logout(HttpServletRequest request) {

        String accessToken = JwtUtils.resolveAccessToken(request);
        String refreshToken = JwtUtils.resolveRefreshToken(request);

        tokenRepository.deleteByRefreshToken(refreshToken);

        return "logout";
    }

    private void verifiedRefreshToken(String encryptedRefreshToken) {
        if (encryptedRefreshToken == null) {
            System.out.println("token not Exist");
//            throw new BusinessLogicException(ExceptionCode.HEADER_REFRESH_TOKEN_NOT_EXISTS);
        }
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
    public String verifyId(String id){
        if(id == null) return "null exception";
        User user = userRepository.findUserById(id);
        if(user == null) return "false";
        else return "true";
//        return userDao.findId(id).toString();
//        return null;
    }

    @Override
    public String verifyNickname(String nickname) {
        if(nickname == null) return "null exception";
        User user = userRepository.findUserByNickname(nickname);
        if(user == null) return "false";
        else return "true";
//        return userDao.findNickname(nickname).toString();
//        return null;
    }

    @Override
    public String verifyEmail(String email) {
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
    public User getUserById(Long id) {
        return userRepository.findById(String.valueOf(id)).get();
//        return userDao.getUserById(id);
//        return null;
    }

    public User getUserByUserid(String id){
        return userRepository.findUserById(id);
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
        System.out.println(userFilter.getGender());
        if(userFilter.getGender()[0] != 0) gender.add(true);
        if(userFilter.getGender()[1] != 0) gender.add(false);

        Date birth = calculateBirthDateFromAge(userFilter.getAge());
        System.out.println(birth);

        Specification<User> spec = (root, query, criteriaBuilder) -> null;

        System.out.println(userFilter.getCareer());
        if(location != null)
            spec = spec.and(UserSpecification.hasLocation(location));
        if(job != null)
            spec = spec.and(UserSpecification.hasJob(job));
        if (gender != null)
            spec = spec.and(UserSpecification.isGender(gender));
        if (birth != null)
            spec = spec.and(UserSpecification.overAge(birth));
        if (userFilter.getCareer() != -1)
            spec = spec.and(UserSpecification.overCareer(userFilter.getCareer()));

        return userRepository.findAll(spec, pageRequest);

    }

    public Date calculateBirthDateFromAge(int age) {
        if(age < 0) return null;

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
    public String getUserId(String id)  {
        //return userDao.getUserId(id);
        return null;
    }

    @Override
    public List<String> getBookmarks(String user) {
        //return userDao.getScraps(user);
        return null;
    }

    @Override
    public Long findUserIdById(String id) {
        return userRepository.findUserById(id).getUserId();
    }


    @Override
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        return userDao.getAllUsers();
//        return null;
    }

}