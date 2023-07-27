package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.GenericTypeIndicator;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Member;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class UserDao {

    public static final String COLLECTION_NAME = "users";

    Firestore db = FirestoreClient.getFirestore();
    public List<User> getUsers() throws ExecutionException, InterruptedException {
        List<User> list = new ArrayList<>();
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(User.class));
        }
        return list;
    }

    public void insertUser(User user) throws ExecutionException, InterruptedException {
        db.collection(COLLECTION_NAME).add(
                user
        );
    }

    public String getUserId(String id) throws ExecutionException, InterruptedException {
        String user = null;
        CollectionReference cities = db.collection(COLLECTION_NAME);
        Query query = cities.whereEqualTo("id", id);
        ApiFuture<QuerySnapshot> querySnapshot = query.get();

        for (DocumentSnapshot document : querySnapshot.get().getDocuments()) {
            user = document.getId();
        }

        return user;
    }


    // 로그인 -> user 정보 찾기
    public User findUser(String id, String pw) throws ExecutionException, InterruptedException {
        User user = null;
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            user = document.toObject(User.class);

            if(user.getId().equals(id)){
                System.out.println(user.getPassword() +"//"+ pw);
                if(user.getPassword().equals(pw)){
                    System.out.println(user.getPassword() + "/" + pw);
                }
                else {
                    user.setId("incorrect password");
                }
                return user;
            }
        }
        return user;
    }

    // 회원가입 -> 중복확인
    public Boolean findId(String id) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            String userId = document.toObject(User.class).getId();
            if(userId!=null){
                if(userId.equals(id))
                    return true;
            }
        }
        return false;
    }

    public Boolean findNickname(String nickname) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            String userNickname = document.toObject(User.class).getNickname();
            if(userNickname!=null){
                if(userNickname.equals(nickname))
                    return true;
            }
        }
        return false;
    }

    public Boolean findEmail(String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            String userEmail = document.toObject(User.class).getEmail();
            if(userEmail!=null){
                if(userEmail.equals(email))
                    return true;
            }
        }
        return false;
    }

    // 인재정보 가져오기. -> 페이징 (20명씩)
    public List<User> getRankScore() throws ExecutionException, InterruptedException {
            ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME)
                    .orderBy("score", Query.Direction.DESCENDING)
                    .limit(20)
                    .get();

            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            List<User> topUsers = new ArrayList<>();

            for (QueryDocumentSnapshot document : documents) {
                System.out.println(document.getId());
                User user = document.toObject(User.class);
                topUsers.add(user);
            }

            return topUsers;
    }

    public List<String> getScraps(String user) throws ExecutionException, InterruptedException{
        DocumentReference docRef = db.collection(COLLECTION_NAME).document(user);
        ApiFuture<DocumentSnapshot> future = docRef.get();
        DocumentSnapshot document = future.get();
        Object obj = document.get("scrap");
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return (List<String>) list;

    }

}
