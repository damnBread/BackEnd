package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface PostService {

    List<Post> getPosts() throws ExecutionException, InterruptedException;
    Long createPost(String writerId, Post postRequest) throws ExecutionException, InterruptedException;

    PostDto getPostById(Long id) throws ExecutionException, InterruptedException;

    Post getPostByIdToEntity(Long id) throws ExecutionException, InterruptedException;
    List<PostDto> findPosts(int page);
    Boolean removePost(Long id);

    PostDto patchPostInfo(Long postId,Map<Object, Object> fields) throws ExecutionException, InterruptedException;
    Page getPostFilter(PostFilter postFilter, int page);

    List<PostDto> getPostByPublisher(Long id);
    Boolean scrap(Long userId, int postNum) throws ExecutionException, InterruptedException;
    User reportReview(Long damnId, String badge) throws ExecutionException, InterruptedException;

    String matchUser(Long damnId, Long userId) throws ExecutionException, InterruptedException;
}