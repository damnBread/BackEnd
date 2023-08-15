package com.example.damnbreadback.service;

import com.example.damnbreadback.repository.PostRepository;
import com.example.damnbreadback.repository.ScrapRepository;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
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

    @Autowired
    private final UserService userService;

    @Autowired
    private final ScrapRepository scrapRepository;

    @Override
    public List<Post> getPosts() throws ExecutionException, InterruptedException {
        return postRepository.findAll();
    }

    @Override
    public Long createPost(String writerId, Post postRequest) throws ExecutionException, InterruptedException {
        postRequest.setPublisher(userService.findUserIdById(writerId));
        return postRepository.save(postRequest).getPostId();
    }

    public Optional<Post> getPostById(Long id) throws ExecutionException, InterruptedException {
        return postRepository.findById(id);
    }

    @Override
    public Page<Post> findStories(int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        return postRepository.findAllByOrderByCreatedDateDesc(pageRequest);
    }

    @Override
    public Boolean bookmark(String name, int postNum) throws ExecutionException, InterruptedException{
        Optional<Post> post = postRepository.findById((long) postNum);
        User user = userService.getUserById(userService.findUserIdById(name));

        if(post.isPresent()) {
            Scrap newScrap = new Scrap(post.get(), user);
            scrapRepository.save(newScrap);
            return true;
        }
        else return false;
    }

}