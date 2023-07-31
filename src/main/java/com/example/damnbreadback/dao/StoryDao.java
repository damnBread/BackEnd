package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Story;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class StoryDao {

    public static final String COLLECTION_NAME = "story";

    public List<Story> getStories() throws ExecutionException, InterruptedException {
        return null;
    }
}
