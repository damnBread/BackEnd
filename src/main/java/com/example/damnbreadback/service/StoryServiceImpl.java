package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.CommentDTO;
import com.example.damnbreadback.dto.PostDto;
import com.example.damnbreadback.dto.StoryDTO;
import com.example.damnbreadback.entity.Comment;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import com.example.damnbreadback.repository.CommentRepository;
import com.example.damnbreadback.repository.StoryRepository;
import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    @Autowired
    private final StoryRepository storyRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final CommentRepository commentRepository;

    @Autowired
    private final UserService userService;
//
//    @Override
//    public List<Story> getAllStories() throws ExecutionException, InterruptedException {
//        return storyDao.getAllStories();
//    }
//
//    @Override
//    public Story getStory(Long id) throws ExecutionException, InterruptedException {
//        return storyDao.getStory(id);
//    }

    public List<StoryDTO> findStories(int page) {
        List<StoryDTO> storyDTOList = new ArrayList<>();
        System.out.println("haha 0");
        PageRequest pageRequest = PageRequest.of(page, 20);
        System.out.println("haha 1");
        List<Story> storyList = storyRepository.findAllByOrderByCreatedDateDesc(pageRequest).getContent();
        System.out.println("haha 2");
        storyList.forEach(p -> {
            storyDTOList.add(StoryDTO.toDTO(p));
        });
        return storyDTOList;
    }

    @Override
    public List<Story> getAllStories() throws ExecutionException, InterruptedException {
        return storyRepository.findAll();
    }

    @Override
    public Optional<Story> getStory(Long id) throws ExecutionException, InterruptedException {
        return storyRepository.findById(id);
    }

    @Override
    public Story createStory(String writerId, StoryDTO uploadRequest) throws ExecutionException, InterruptedException {
        Story story = new Story();
        story.setTitle(uploadRequest.getTitle());
        story.setContent(uploadRequest.getContent());
        story.setWriter(userService.findUserIdById(writerId));
        return storyRepository.save(story);
    }

    @Override
    public boolean createComment(CommentDTO commentDTO) throws ExecutionException, InterruptedException {
        try{
            Optional<Story> story = storyRepository.findById(commentDTO.getStoryId());
            User user = userRepository.findUserByUserId(commentDTO.getWriterId());
            if(story.isPresent() && user != null){

                commentRepository.save(Objects.requireNonNull(Comment.toEntity(commentDTO, story.get(), user)));
                return true;
            }
            return false;
        }
        catch (Error e){
            return false;
        }
    }
}