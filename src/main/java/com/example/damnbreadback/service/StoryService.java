package com.example.damnbreadback.service;

import com.example.damnbreadback.dto.CommentDTO;
import com.example.damnbreadback.dto.StoryDTO;
import com.example.damnbreadback.entity.Story;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface StoryService {

    public Page<Story> findStories(int page);

    public List<Story> getAllStories() throws ExecutionException, InterruptedException;
    public Optional<Story> getStory(Long id) throws ExecutionException, InterruptedException;
    public Story createStory(String writerId, StoryDTO uploadRequest) throws ExecutionException, InterruptedException;

    public boolean createComment(CommentDTO commentDTO) throws ExecutionException, InterruptedException;
}