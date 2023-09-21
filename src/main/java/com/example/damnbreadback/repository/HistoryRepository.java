package com.example.damnbreadback.repository;

import com.example.damnbreadback.dto.HistoryDto;
import com.example.damnbreadback.entity.History;
import com.example.damnbreadback.entity.Post;
import com.example.damnbreadback.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@EnableJpaRepositories
@Transactional(readOnly = true)
public interface HistoryRepository extends JpaRepository<History, Long> {

//    History findHistoryByHistoryId(Long historyId);
    List<History> findByUserUserId(Long userId);

    @Query(value = "SELECT h FROM History h WHERE h.post.postId = :postId and h.user.userId = :userId")
    History findByUserIdAndPostId(@Param("userId") Long userId, @Param("postId") Long postId);

    void deleteByPostPostId(Long damnId);

}
