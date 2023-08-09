package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Story;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
public class StoryDao {

    //public static final String COLLECTION_NAME = "story";

    @Autowired
    private SessionFactory sessionFactory;

    public List<Story> getAllStories() throws ExecutionException, InterruptedException {
        Session session = sessionFactory.getCurrentSession();
        String hql = "from Story";

        Query<Story> query = session.createQuery(hql, Story.class);
        List<Story> storyList = query.getResultList();

        return storyList;
    }

    public Story getStory(Long storyId) throws ExecutionException, InterruptedException {
        Session session = sessionFactory.getCurrentSession();
        Story story = (Story) session.get(Story.class, storyId);

        return story;
    }

    public List<Story> getStories(int page) throws ExecutionException, InterruptedException {
        Session session = sessionFactory.getCurrentSession();

        return null;
    }
}
