package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Scrap;
import com.example.damnbreadback.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @Override
    <S extends Scrap> S save(S entity);

    @Override
    void delete(Scrap entity);

    List<Scrap> findAllByUserAndPost(User user, Post post);
}
