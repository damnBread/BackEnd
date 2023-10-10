package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.UserDTO;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private final UserService userService;
    @Autowired
    private final PostService postService;
    @Autowired
    private final HistoryRepository historyRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Override
    public List<PostDto> getHistory(Long userId) throws ExecutionException, InterruptedException {
        List<History> historyList = historyRepository.findByUserUserId(userId);
        List<Post> posts = new ArrayList<>();
        List<PostDto> postDtoList = new ArrayList<>();

        for (History history : historyList) {
            Post post = history.getPost();
            posts.add(post);
        }

        posts.forEach(p -> {
            postDtoList.add(PostDto.toDTO(p));
        });

        return postDtoList;
    }

    public HistoryDto patchStatus(Long postId, Long userId, int statusCode) {
        HistoryDto history = HistoryDto.toDTO(historyRepository.findByUserIdAndPostId(userId, postId));

        User user = userRepository.findUserByUserId(userId);
        Optional<Post> optionalPost = postRepository.findById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (history != null) {
//                history.setStatus_code(statusCode);
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

    public Boolean isUnique(Long damnid, Long userid){
        return historyRepository.findHistoryByUserUserIdAndPostPostId(userid, damnid) == null;
    }

    public Long createHistory(Long damnId, Long userId) throws ExecutionException, InterruptedException {
        if(isUnique(damnId, userId)){
            User user = User.toEntity(userService.getUserById(userId));
            Post post = postService.getPostByIdToEntity(damnId);

            if(post != null) {
                if(post.getPublisher().equals(userId)){
                    return -1L; // 공고게시자가 지원
                }
                else {
                    History history = new History();
                    history.setPost(post);
                    history.setUser(user);

                    return historyRepository.save(history).getHistoryId();
                }
            }
            else return -2L; // 올바르지 않는 post
        }
        else {
            return -3L; // 이미 존재하는 지원 데이터
        }
    }

}