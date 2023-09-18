package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HistoryRepository extends JpaRepository<History, Long>, JpaSpecificationExecutor {

    List<History> findByUserUserId(Long userId);
    History findByUserUserIdAndPostPostId(Long userId, Long postId);

    void deleteByPostPostId(Long damnId);

}
