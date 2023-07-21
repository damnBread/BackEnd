package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface PostService {

    List<Post> getPosts() throws ExecutionException, InterruptedException;
    void createPost(Post post) throws ExecutionException, InterruptedException;

}