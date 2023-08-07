package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.PostRepository;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    @Autowired
    private final PostRepository postRepository;

    @Override
    public List<Post> getPosts() throws ExecutionException, InterruptedException {
        return postRepository.findAll();
    }

    @Override
    public Long createPost(Post post) throws ExecutionException, InterruptedException {
        return postRepository.save(post).getPostId();
    }

    public Optional<Post> getPostById(Long id) throws ExecutionException, InterruptedException {
        return postRepository.findById(id);
    }

    @Override
    public Page<Post> findStories(int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        return postRepository.findAllByOrderByCreatedDateDesc(pageRequest);
    }

}