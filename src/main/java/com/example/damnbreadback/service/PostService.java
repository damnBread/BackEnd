package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface PostService {

    List<Post> getPosts() throws ExecutionException, InterruptedException;
    Long createPost(String writerId, Post postRequest) throws ExecutionException, InterruptedException;

    Optional<Post> getPostById(Long id) throws ExecutionException, InterruptedException;
    Page<Post> findPosts(int page);
    Page getPostFilter(PostFilter postFilter, int page);

    Boolean deletePost(Long id) throws ExecutionException, InterruptedException;

    Boolean bookmark(String name, int postNum, Boolean isDelete) throws ExecutionException, InterruptedException;
}