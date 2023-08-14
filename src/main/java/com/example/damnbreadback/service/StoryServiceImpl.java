package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.StoryRepository;
import com.example.damnbreadback.dto.StoryDTO;
import com.example.damnbreadback.repository.StoryRepository;
import com.example.damnbreadback.entity.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    @Autowired
    private final StoryRepository storyRepository;

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

    public Page<Story> findStories(int page) {
        PageRequest pageRequest = PageRequest.of(page, 20);
        return storyRepository.findAllByOrderByCreatedDateDesc(pageRequest);
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
}