package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    List<Post> findAll();

    @Override
    Optional<Post> findById(Long postId);

    @Override
    <S extends Post> S save(S entity);

    // 최신 순으로 가져오기 -페이징-
    Page<Post> findAllByOrderByCreatedDateDesc(PageRequest pageable);
}
