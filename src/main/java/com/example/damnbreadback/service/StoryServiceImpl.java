package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.StoryDao;
import com.example.damnbreadback.entity.Story;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class StoryServiceImpl implements StoryService {

    @Autowired
    private final StoryDao storyDao;

    @Override
    public List<Story> getAllStories() throws ExecutionException, InterruptedException {
        return storyDao.getAllStories();
    }
}