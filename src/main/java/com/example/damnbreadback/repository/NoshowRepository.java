package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Noshow;
import com.example.damnbreadback.entity.User;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface NoshowRepository extends JpaRepository<Noshow, String> , JpaSpecificationExecutor{


}
