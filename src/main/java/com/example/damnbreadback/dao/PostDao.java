package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class PostDao {

    public static final String COLLECTION_NAME = "post";

    public List<Post> getPosts() throws ExecutionException, InterruptedException {
        List<Post> list = new ArrayList<>();
        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION_NAME).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Post.class));
        }
        return list;
    }

    public String createPosts(Post post) throws ExecutionException, InterruptedException{

        Firestore firestore = FirestoreClient.getFirestore();
        ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture =
                firestore.collection(COLLECTION_NAME).document(post.getId()).set(post);
        return apiFuture.get().getUpdateTime().toString();
    }
}
