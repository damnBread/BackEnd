package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Story;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public interface StoryService {

    public Page<Story> findStories(int page);

    public List<Story> getAllStories() throws ExecutionException, InterruptedException;
    public Optional<Story> getStory(Long id) throws ExecutionException, InterruptedException;
    public Story createStory(Story story) throws ExecutionException, InterruptedException;

}