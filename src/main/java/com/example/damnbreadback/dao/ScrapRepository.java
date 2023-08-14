package com.example.damnbreadback.dao;

import com.example.damnbreadback.entity.Scrap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScrapRepository extends JpaRepository<Scrap, Long> {

    @Override
    <S extends Scrap> S save(S entity);
}
