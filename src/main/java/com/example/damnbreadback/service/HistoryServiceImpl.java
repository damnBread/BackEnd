package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.HistoryRepository;
import com.example.damnbreadback.repository.PostRepository;
import com.example.damnbreadback.repository.PostSpecification;
import com.example.damnbreadback.repository.ScrapRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private final HistoryRepository historyRepository;

    @Override
    public List<Post> getHistory(Long userId) throws ExecutionException, InterruptedException {
        List<History> historyList = historyRepository.findByUserUserId(userId);
        List<Post> posts = new ArrayList<>();

        for (History history : historyList) {
            Post post = history.getPost();
            if (post != null) {
                posts.add(post);
            }
        }
        System.out.println(posts.size());
        return posts;
    }

    public History patchStatus(Long postId, Long userId, int statusCode){
        //TODO 이것부터해. status 수정.
        System.out.println(userId+ "//" +  postId );
        History history = historyRepository.findByUserUserIdAndPostPostId(userId, postId);

        if (history != null) {
            history.setStatusCode(statusCode);
            historyRepository.save(history);
            return history;
        } else {
            return null;
        }



    }

//    @Autowired
//    private final ScrapRepository scrapRepository;



}