package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.User;
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
        // 모든 게시물 가져오기

        Session session = sessionFactory.getCurrentSession();
        String hql = "from User";

        Query<User> query = session.createQuery(hql, User.class);
        List<User> userList = query.getResultList();

        return userList;
    }

//    public List<User> getPagedPosts(int page) throws ExecutionException, InterruptedException {
//        // 특정 페이지 (20개) 게시물 가져오기
//
//        return null;
//    }
//
//    public List<Post> getFilteredPosts() throws ExecutionException, InterruptedException {
//        // 필터링된 게시물 가져오기
//
//        return null;
//    }

    public User getUserById(String userId) throws ExecutionException, InterruptedException{

        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, userId);

        return user;
    }

//    public static final String COLLECTION_NAME = "users";
//
//    Firestore db = FirestoreClient.getFirestore();
//    public List<User> getUsers() throws ExecutionException, InterruptedException {
//        List<User> list = new ArrayList<>();
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            list.add(document.toObject(User.class));
//        }
//        return list;
//    }
//
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
//
//    public String getUserId(String id) throws ExecutionException, InterruptedException {
//        String user = null;
//        CollectionReference cities = db.collection(COLLECTION_NAME);
//        Query query = cities.whereEqualTo("id", id);
//        ApiFuture<QuerySnapshot> querySnapshot = query.get();
//
//        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
//            user = document.getId();
//        }
//
//        return user;
//    }
//
//
    // 로그인 -> user 정보 찾기
    public User findUser(String id, String pw) throws ExecutionException, InterruptedException {
        EntityManager entityManager = sessionFactory.createEntityManager();

        String hql = "FROM User WHERE id = :userId AND pw = :password";
        TypedQuery<User> query = entityManager.createQuery(hql, User.class);
        query.setParameter("userId", id);
        query.setParameter("password", pw);

        List<User> userList = query.getResultList();

        System.out.println(userList);
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
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where id = :userId";

        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("userId", id);

        List<User> userList = query.getResultList();

        return !userList.isEmpty();
    }

    public Boolean findNickname(String nickname) throws ExecutionException, InterruptedException {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where nickname = :nickname";

        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("nickname", nickname);

        List<User> userList = query.getResultList();

        return !userList.isEmpty();
    }

    public Boolean findEmail(String email) throws ExecutionException, InterruptedException {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from User where email = :email";

        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("email", email);

        List<User> userList = query.getResultList();

        return !userList.isEmpty();
    }
//
//    // 인재정보 가져오기. -> 페이징 (20명씩)
//    public List<User> getRankScore() throws ExecutionException, InterruptedException {
//            ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
//                    .orderBy("score", Query.Direction.DESCENDING)
//                    .limit(20)
//                    .get();
//
//            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//            List<User> topUsers = new ArrayList<>();
//
//            for (QueryDocumentSnapshot document : documents) {
//                System.out.println(document.getId());
//                User user = document.toObject(User.class);
//                topUsers.add(user);
//            }
//
//            return topUsers;
//    }
//
//    public List<String> getScraps(String user) throws ExecutionException, InterruptedException{
//        DocumentReference docRef = db.collection(COLLECTION_NAME).document(user);
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//        Object obj = document.get("scrap");
//        List<?> list = new ArrayList<>();
//        if (obj.getClass().isArray()) {
//            list = Arrays.asList((Object[])obj);
//        } else if (obj instanceof Collection) {
//            list = new ArrayList<>((Collection<?>)obj);
//        }
//        return (List<String>) list;
//
//    }



//    public static final String COLLECTION_NAME = "users";
//
//    Firestore db = FirestoreClient.getFirestore();
//    public List<User> getUsers() throws ExecutionException, InterruptedException {
//        List<User> list = new ArrayList<>();
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            list.add(document.toObject(User.class));
//        }
//        return list;
//    }
//
//    public User getUserByUserId(String id) throws ExecutionException, InterruptedException {
//        User user = null;
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            user = document.toObject(User.class);
//
//            if(user.getId().equals(id)){
//                return user;
//            }
//        }
//        return user;
//    }
//
//
//    public void insertUser(User user) throws ExecutionException, InterruptedException {
//        db.collection(COLLECTION_NAME).add(
//                user
//        );
//    }
//
//    public String getUserId(String id) throws ExecutionException, InterruptedException {
//        String user = null;
//        CollectionReference cities = db.collection(COLLECTION_NAME);
//        Query query = cities.whereEqualTo("id", id);
//        ApiFuture<QuerySnapshot> querySnapshot = query.get();
//
//        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
//            user = document.getId();
//        }
//
//        return user;
//    }
//
//
//    // 로그인 -> user 정보 찾기
//    public User findUser(String id, String pw) throws ExecutionException, InterruptedException {
//        User user = null;
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            user = document.toObject(User.class);
//            String userId = document.toObject(User.class).getId();
//            String userPw = document.toObject(User.class).getPw();
//            System.out.println("gkgkg" + userId + "///" + userPw);
//            System.out.println("param" + id + "///" + pw);
//            if(userId!=null && userPw!= null){
//                if(userId.equals(id)){
//                    if(userPw.equals(pw)){
//                        return user;
//                    }
//                    else user.setId("incorrect password");
//                }
//                else user.setId("fail to find user");
//            }
//            else user.setId("db null exception");
//
//        }
//        return user;
//    }
//
//    // 회원가입 -> 중복확인
//    public Boolean findId(String id) throws ExecutionException, InterruptedException {
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            String userId = document.toObject(User.class).getId();
//            if(userId!=null){
//                if(userId.equals(id))
//                    return true;
//            }
//        }
//        return false;
//    }
//
//    public Boolean findNickname(String nickname) throws ExecutionException, InterruptedException {
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            String userNickname = document.toObject(User.class).getNickname();
//            System.out.println(userNickname + "??" + nickname);
//            if(userNickname!=null){
//                if(userNickname.equals(nickname))
//                    return true;
//            }
//        }
//        return false;
//    }
//
//    public Boolean findEmail(String email) throws ExecutionException, InterruptedException {
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            String userEmail = document.toObject(User.class).getEmail();
//            if(userEmail!=null){
//                if(userEmail.equals(email))
//                    return true;
//            }
//        }
//        return false;
//    }
//
//    // 인재정보 가져오기. -> 페이징 (20명씩)
//    public List<User> getRankScore() throws ExecutionException, InterruptedException {
//            ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
//                    .orderBy("score", Query.Direction.DESCENDING)
//                    .limit(20)
//                    .get();
//
//            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//            List<User> topUsers = new ArrayList<>();
//
//            for (QueryDocumentSnapshot document : documents) {
//                System.out.println(document.getId());
//                User user = document.toObject(User.class);
//                topUsers.add(user);
//            }
//
//            return topUsers;
//    }
//
//    public List<String> getScraps(String user) throws ExecutionException, InterruptedException{
//        DocumentReference docRef = db.collection(COLLECTION_NAME).document(user);
//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        DocumentSnapshot document = future.get();
//        Object obj = document.get("scrap");
//        List<?> list = new ArrayList<>();
//        if (obj.getClass().isArray()) {
//            list = Arrays.asList((Object[])obj);
//        } else if (obj instanceof Collection) {
//            list = new ArrayList<>((Collection<?>)obj);
//        }
//        return (List<String>) list;
//
//    }

}
