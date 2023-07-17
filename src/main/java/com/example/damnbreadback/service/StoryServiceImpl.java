package com.example.damnbreadback.service;

import com.example.damnbreadback.dao.StoryDao;
import com.example.damnbreadback.dao.UserDao;
import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.entity.User;
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
    public List<Story> getStories() throws ExecutionException, InterruptedException {
        return storyDao.getStories();
    }
}