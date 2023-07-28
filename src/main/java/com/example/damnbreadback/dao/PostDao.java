package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class PostDao {

    public static final String COLLECTION_NAME = "post";

    public List<Post> getPosts() throws ExecutionException, InterruptedException {
        return null;
    }

    public String createPosts(Post post) throws ExecutionException, InterruptedException{
        return null;
    }

    public Post getPost(String postName) throws ExecutionException, InterruptedException{

        return null;
    }
}
