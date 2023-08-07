package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface PostService {

    List<Post> getPosts() throws ExecutionException, InterruptedException;
    Long createPost(Post post) throws ExecutionException, InterruptedException;

    Optional<Post> getPostById(Long id) throws ExecutionException, InterruptedException;
    Page<Post> findStories(int page);
}