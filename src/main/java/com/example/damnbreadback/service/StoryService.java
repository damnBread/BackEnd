package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Story;
import com.example.damnbreadback.entity.User;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StoryService {

    List<Story> getStories() throws ExecutionException, InterruptedException;

}