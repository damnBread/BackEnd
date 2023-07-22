package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.PostDao;
import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
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
        return postDao.getPosts();
    }

    @Override
    public void createPost(Post post) throws ExecutionException, InterruptedException {
        postDao.createPosts(post);
    }

    @Override
    public Post getPost(String postName) throws ExecutionException, InterruptedException {
        return postDao.getPost(postName);
    }

}