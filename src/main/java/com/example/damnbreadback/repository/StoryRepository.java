package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

    @Override
    List<Story> findAll();

    @Override
    Optional<Story> findById(Long storyId);

    Page<Story> findAllByOrderByCreatedDateDesc(PageRequest pageable);


    @Override
    <S extends Story> S save(S entity);
}
