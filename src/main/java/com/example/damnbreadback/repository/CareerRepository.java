package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Career;
import com.example.damnbreadback.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CareerRepository extends JpaRepository<Career, Long>, JpaSpecificationExecutor {

//    void deleteByUserUserId(Long userId);
    List<Career> findCareersByUserId(Long userId);

}
