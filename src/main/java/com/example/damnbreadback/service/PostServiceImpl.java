package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.PostDao;
import com.example.damnbreadback.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostDao postDao;

    @Override
    public List<Post> getPosts() throws ExecutionException, InterruptedException {
        return postDao.getAllPosts();
    }

    @Override
    public Long createPost(Post post) throws ExecutionException, InterruptedException {
        return postDao.createPosts(post);
    }

    @Override
    public Post getPostById(String postName) throws ExecutionException, InterruptedException {
        return postDao.getPostById(postName);
    }

}