package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.PostFilter;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.*;
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

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Override
    public List<Post> getHistory(Long userId) throws ExecutionException, InterruptedException {
        List<History> historyList = historyRepository.findByUserUserId(userId);
        List<Post> posts = new ArrayList<>();

        for (History history : historyList) {
            Post post = history.getPost();
            posts.add(post);
        }
        return posts;
    }

    public HistoryDto patchStatus(Long postId, Long userId, int statusCode) {
        HistoryDto history = HistoryDto.toDTO(historyRepository.findByUserIdAndPostId(userId, postId));

        User user = userRepository.findUserByUserId(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (history != null) {
                history.setStatus_code(statusCode);
                historyRepository.save(History.toEntity(history, user, post));
                return history;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public List<UserDTO> getUserByHistory(Long damnId){
        List<User> userList = historyRepository.findUserByPostId(damnId);
        List<UserDTO> userDTOList = new ArrayList<>();

        userList.forEach(u -> {
            userDTOList.add(UserDTO.toDTO(u));
        });

        return userDTOList;
    }

}