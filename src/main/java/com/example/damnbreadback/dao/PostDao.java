package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;


import java.util.*;
import java.util.concurrent.ExecutionException;

@Repository
@Slf4j
//@ComponentScan(basePackages={"com.example.damnbreadback.dao"})
public class PostDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Post> getAllPosts() throws ExecutionException, InterruptedException {
        // 모든 게시물 가져오기

        Session session = sessionFactory.getCurrentSession();
        String hql = "from Post";

        Query<Post> query = session.createQuery(hql, Post.class);
        List<Post> postList = query.getResultList();

        return postList;
    }

    public List<Post> getPagedPosts(int page) throws ExecutionException, InterruptedException {
        // 특정 페이지 (20개) 게시물 가져오기

        return null;
    }

    public List<Post> getFilteredPosts() throws ExecutionException, InterruptedException {
        // 필터링된 게시물 가져오기

        return null;
    }

    public Long createPosts(Post post) throws ExecutionException, InterruptedException{
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(post);
        session.flush();
        return id;
    }

    public Post getPostById(String postId) throws ExecutionException, InterruptedException{

        Session session = sessionFactory.getCurrentSession();
        Post post = (Post) session.get(Post.class, postId);

        return post;
    }
}
