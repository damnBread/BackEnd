package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @Override
    <S extends Scrap> S save(S entity);

    List<Scrap> getScrapsByUserUserId(Long userId);
}
