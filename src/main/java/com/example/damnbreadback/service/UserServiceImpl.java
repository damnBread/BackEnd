package com.example.damnbreadback.service;

import com.example.damnbreadback.config.JwtUtils;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.RefreshToken;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.repository.ScrapRepository;
import com.example.damnbreadback.repository.TokenRepository;
import com.example.damnbreadback.repository.UserRepository;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.dto.UserFilter;
import com.example.damnbreadback.repository.UserSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ReflectionUtils;

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

    @Autowired
    UserRepository userRepository;
    @Autowired
    TokenRepository tokenRepository;

    @Autowired
    TokenService tokenService;
    @Autowired
    ScrapRepository scrapRepository;


    // 로그인 ===============================================================================
    public String login(String id, String pw, HttpServletResponse response) throws ExecutionException, InterruptedException{
        //인증과정 생략
        UserDTO user = loginCheck(id, pw);

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
    public UserDTO loginCheck(String id, String pw){
        User userEntity = userRepository.findUserById(id);
        if(userEntity == null) return null;

        UserDTO user = UserDTO.toDTO(userEntity);
        if(user == null) return null;

        if(user.getPw().equals(pw)) return user;
        else return null;

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
        System.out.println(user);
        if(user == null) return "false";
        else return "true";
//        return userDao.findEmail(email).toString();
//        return null;
    }


    // 회원가입 -> 신규 회원 저장
    @Override
    public UserDTO addUser(UserDTO user) {
        if(user.getId() == null || user.getBirth() == null ||
                user.getEmail() == null || user.getHome() == null ||
                user.getHopeJob() == null || user.getNickname() == null ||
                user.getPw() == null || user.getPhone() == null ||
                user.getName() == null || user.getHopeLocation() == null) {
            return null;
        }
        userRepository.save(User.toEntity(user));

        return user;
    }

    @Transactional
    public UserDTO patchUserInfo(String id, Map<Object, Object> fields) throws ExecutionException, InterruptedException {
        UserDTO targetUser = UserDTO.toDTO(userRepository.findById(String.valueOf(findUserIdById(id))).get());
        if(targetUser == null) return null;

        fields.forEach((key, value) -> {
            if (key.equals("nickname")) {
                targetUser.setNickname((String)value);
            }
            if (key.equals("pw")) {
                targetUser.setPw((String)value);
            }
            if (key.equals("email")) {
                targetUser.setEmail((String)value);
            }
            if (key.equals("phone")) {
                targetUser.setPhone((String)value);
            }
            if (key.equals("home")) {
                targetUser.setHome((String)value);
            }
            if (key.equals("introduce")) {
                targetUser.setIntroduce((String)value);
            }
            if (key.equals("hopeJob")) {
                targetUser.setHopeJob((String)value);
            }
            if (key.equals("hopeLocation")) {
                targetUser.setHopeLocation((String)value);
            }
            if (key.equals("isPublic")) {
                targetUser.setIsPublic((String)value);
            }


        });

        userRepository.save(User.toEntity(targetUser));


        System.out.println("target ::: " + targetUser.getUserId());

        if (targetUser == null) {
            return null;
        }

        return targetUser;
    }

    // ====================================================================================================


    //기본키 userId로 user 찾기.
    @Override
    public UserDTO getUserById(Long id) {
        return UserDTO.toDTO(userRepository.findById(String.valueOf(id)).get());
//        return userDao.getUserById(id);
//        return null;
    }

    public UserDTO getUserByUserid(String id){
        return UserDTO.toDTO(userRepository.findUserById(id));
//        return userDao.getUserById(id);
//        return null;
    }

    // 인재정보 -> rank 정보 get
    @Override
    public List<UserDTO> getRankScore(int page){
        PageRequest pageRequest = PageRequest.of(page, 20);
//        return userRepository.findAll(pageRequest);

        List<UserDTO> userDTOList = new ArrayList<UserDTO>();
        List<User> userList = userRepository.findUsersByOrderByScoreDesc(pageRequest);
        for(User u : userList){
            userDTOList.add(UserDTO.toDTO(u));
        }
        return userDTOList;
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
    public List<Scrap> getScraps(Long user) {
        //return userDao.getScraps(user);
        System.out.println("ksflkadjfk;adf" + scrapRepository.getScrapsByUserUserId(user));
        return scrapRepository.getScrapsByUserUserId(user);
    }

    @Override
    public Long findUserIdById(String id) {
        return userRepository.findUserById(id).getUserId();
    }


    @Override
    public List<UserDTO> getUsers() throws ExecutionException, InterruptedException {
//        return userDao.getAllUsers();
        return null;
    }

}