package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Post;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PostService {

    List<Post> getPosts() throws ExecutionException, InterruptedException;
    Long createPost(Post post) throws ExecutionException, InterruptedException;

    Post getPostById(String postName) throws ExecutionException, InterruptedException;
}