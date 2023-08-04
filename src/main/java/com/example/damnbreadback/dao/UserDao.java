package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
@ComponentScan(basePackages={"com.example.damnbreadback.dao"})
public class UserDao {
    @Autowired
    private SessionFactory sessionFactory;

    public List<User> getAllUsers() throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();

        String hql = "FROM User";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);

        List<User> userList = query.getResultList();
        return userList;
    }

    public User getUserById(Long userId) throws ExecutionException, InterruptedException{
        EntityManager entityManager = sessionFactory.createEntityManager();

        String hql = "FROM User WHERE userId = :userId";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("userId", userId);

        List<User> userList = query.getResultList();
        System.out.println(userList.size());

        if (userList.isEmpty()) {
            User user = new User();
            user.setId("fail to find user");
            return user;
        } else {
            return userList.get(0);
        }
    }

    public String insertUser(User user) throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
            return user.getId(); // Assuming that the User entity has an auto-generated ID field.
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw e;
        } finally {
            entityManager.close();
        }
    }

    // 로그인 -> user 정보 찾기
    public User findUser(String id, String pw) throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();

        String hql = "FROM User WHERE id = :userId AND pw = :password";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("userId", id);
        query.setParameter("password", pw);

        List<User> userList = query.getResultList();
        System.out.println(userList.size());

        if (userList.isEmpty()) {
            User user = new User();
            user.setId("fail to find user");
            return user;
        } else {
            return userList.get(0);
        }
    }
//
    // 회원가입 -> 중복확인
    public Boolean findId(String id) throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();

        System.out.println("id :: " +id);
        String hql = "FROM User where id = :userId";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);

        query.setParameter("userId", id);

        List<User> userList = query.getResultList();

        return !userList.isEmpty();
    }

    public Boolean findNickname(String nickname) throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();
        System.out.println("nickname :: " +nickname);
        String hql = "FROM User where nickname = :nickname";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("nickname", nickname);

        List<User> userList = query.getResultList();

        return !userList.isEmpty();
    }

    public Boolean findEmail(String email) throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();
        System.out.println("email :: " +email);
        String hql = "FROM User where email = :email";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("email", email);

        List<User> userList = query.getResultList();

        return !userList.isEmpty();
    }
//
    // 인재정보 가져오기. -> 페이징 (20명씩)
    public List<User> getRankScore(int page) throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();

        // user 테이블에서 score 로 내림차순 정렬 (score가 같을 경우 joinDate가 느린 사람이 상위로 정렬) 하여 상위 20명의 데이터 가져오기.
        String hql = "FROM User u " +
                "ORDER BY u.score DESC, " +
                "CASE WHEN u.score = (SELECT MAX(u2.score) FROM User u2) THEN u.joinDate END DESC ";

        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setFirstResult(page-1);
        query.setMaxResults(20);
        List<User> userList = query.getResultList();

        System.out.println(userList);

        return userList;
    }


}
