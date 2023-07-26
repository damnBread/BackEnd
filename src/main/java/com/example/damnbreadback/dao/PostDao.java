package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Repository
@Slf4j
public class PostDao {

    public static final String COLLECTION_NAME = "post";

    public List<Post> getPosts(int page) throws ExecutionException, InterruptedException, TimeoutException {
        List<Post> list = new ArrayList<>();
        Firestore db = FirestoreClient.getFirestore();
//        ApiFuture<QuerySnapshot> future = db.collection(COLLECTION_NAME).get();
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//        for (QueryDocumentSnapshot document : documents) {
//            list.add(document.toObject(Post.class));
//        }
//        return list;

        CollectionReference collection = db.collection(COLLECTION_NAME);
        Query seee = collection.orderBy("publishDate", Query.Direction.DESCENDING).startAt((page-1)*20).limit(5);

        ApiFuture<QuerySnapshot> future = seee.get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            list.add(document.toObject(Post.class));
        }
        return list;
    }

    public String createPosts(Post post) throws ExecutionException, InterruptedException{

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference addedDocRef = db.collection(COLLECTION_NAME).document();
        System.out.println("Added document with ID: " + addedDocRef.getId());

        //ApiFuture<com.google.cloud.firestore.WriteResult> apiFuture =
        //        firestore.collection(COLLECTION_NAME).document(post.getId()).set(post);

        ApiFuture<WriteResult> writeResult = addedDocRef.set(post);
        return writeResult.get().getUpdateTime().toString();
    }

    public Post getPost(String postName) throws ExecutionException, InterruptedException{

        Firestore db = FirestoreClient.getFirestore();

        DocumentReference docRef = db.collection(COLLECTION_NAME).document(postName);
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
