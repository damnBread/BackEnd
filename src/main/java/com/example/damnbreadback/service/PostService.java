package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

public interface PostService {

    List<Post> getPosts(int page) throws ExecutionException, InterruptedException, TimeoutException;
    void createPost(Post post) throws ExecutionException, InterruptedException;

    Post getPost(String postName) throws ExecutionException, InterruptedException;
}