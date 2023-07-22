package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
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

        DocumentReference addedDocRef = firestore.collection(COLLECTION_NAME).document();
        System.out.println("Added document with ID: " + addedDocRef.getId());

        //ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture =
        //        firestore.collection(COLLECTION_NAME).document(post.getId()).set(post);

        ApiFuture<WriteResult> writeResult = addedDocRef.set(post);
        return writeResult.get().getUpdateTime().toString();
    }

    public Post getPost(String postName) throws ExecutionException, InterruptedException{

        Firestore firestore = FirestoreClient.getFirestore();

        DocumentReference docRef = firestore.collection(COLLECTION_NAME).document(postName);
// asynchronously retrieve the document
        ApiFuture<DocumentSnapshot> future = docRef.get();
// block on response
        DocumentSnapshot document = future.get();
        Post post = null;
        if (document.exists()) {
            // convert document to POJO
            post = document.toObject(Post.class);
            System.out.println(post);
        } else {
            System.out.println("No such document!");
        }

        return post;
    }
}
