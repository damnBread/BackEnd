package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Comment;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

//    List<Comment> findAllByStory(Long id);

    @Query(value = "SELECT c FROM Comment c WHERE c.story.id = :storyId")
    List<Comment> findAllByStory(@Param("storyId") Long storyId);
}
