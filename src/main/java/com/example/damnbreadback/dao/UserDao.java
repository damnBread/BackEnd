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


    public User findUser(String id, String pw) throws ExecutionException, InterruptedException {
        User user = null;
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            user = document.toObject(User.class);
            if(user.getId().equals(id)){
                if(user.getPassword().equals(pw)){
                    return user;
                }
                else {
                    user.setId("incorrect password");
                    return user;
                }
            }
        }
        return user;
    }

    public Boolean findId(String id) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if(document.toObject(User.class).getId().equals(id)){
                return true;
            }
        }
        return false;
    }

    public Boolean findNickname(String nickname) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if(document.toObject(User.class).getNickname().equals(nickname)){
                return true;
            }
        }
        return false;
    }

    public Boolean findEmail(String email) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            if(document.toObject(User.class).getEmail().equals(email)){
                return true;
            }
        }
        return false;
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

//        ApiFuture<DocumentSnapshot> future = docRef.get();
//        List<String> scraps = null;
//
//        DocumentSnapshot document = future.get();
//        if (document.exists()) {
//            scraps = document.get("scrap", List.class);
//        } else {
//            return null;
//        }

    }

}
