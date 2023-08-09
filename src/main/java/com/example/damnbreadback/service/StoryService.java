package com.example.damnbreadback.service;

import com.example.damnbreadback.entity.Story;

import java.util.List;
import java.util.concurrent.ExecutionException;

public interface StoryService {

    List<Story> getAllStories() throws ExecutionException, InterruptedException;

}