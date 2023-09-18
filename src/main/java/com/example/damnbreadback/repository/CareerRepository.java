package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<History, Long>, JpaSpecificationExecutor {

    void deleteByUserUserId(Long userId);

}
