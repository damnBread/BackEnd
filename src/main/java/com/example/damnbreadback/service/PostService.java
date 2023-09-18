package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.Post;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface PostService {

    List<Post> getPosts() throws ExecutionException, InterruptedException;
    Long createPost(String writerId, Post postRequest) throws ExecutionException, InterruptedException;

    Optional<Post> getPostById(Long id) throws ExecutionException, InterruptedException;
    Page<Post> findPosts(int page);
    Boolean removePost(Long id);

    Post patchPostInfo(Long id, Map<Object, Object> fields) throws ExecutionException, InterruptedException;
    Page getPostFilter(PostFilter postFilter, int page);

    List<Post> getPostByPublisher(Long id);
    Boolean bookmark(String name, int postNum) throws ExecutionException, InterruptedException;
}