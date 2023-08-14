package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.User;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> , JpaSpecificationExecutor{
    User findUserById(String id);
    User findUserByNickname(String nickname);
    User findUserByEmail(String email);

    @Query(value = "SELECT * FROM User u ORDER BY u.score DESC, CASE WHEN u.score = (SELECT MAX(u2.score) FROM User u2) THEN u.join_date END DESC",
            nativeQuery = true)
    List<User> findUsersByOrderByScoreDesc(PageRequest pageable);

//    @Query(value = "SELECT * FROM User u " +
//            "WHERE (u.hope_location Like CONCAT('%', :location, '%')  OR u.hope_location Like CONCAT('%', :location, '%') OR u.hope_location Like CONCAT('%', :location, '%')) " +
//            "AND (u.hope_job Like CONCAT('%', :job, '%') OR u.hope_job Like CONCAT('%', :job, '%') OR u.hope_job  Like CONCAT('%', :job, '%')) " +
//            "AND (u.gender = :gender OR u.gender = :gender) AND u.birth <= :birth " +
//            "ORDER BY u.score DESC, CASE WHEN u.score = (SELECT MAX(u2.score) FROM User u2) THEN u.join_date END DESC",
//            nativeQuery = true)
//    List<User> findUsersByOrderByScoreDescFilter(List<String> location, List<String> job, List<Boolean> gender, Date birth, PageRequest pageable);
}
