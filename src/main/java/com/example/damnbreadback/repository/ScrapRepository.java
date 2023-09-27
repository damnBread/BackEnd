package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @Override
    <S extends Scrap> S save(S entity);

    @Query(value = "SELECT s from Scrap s where s.user.userId = :userId")
    List<Scrap> getScrapsByUserUserId(@Param("userId") Long userId);
}
