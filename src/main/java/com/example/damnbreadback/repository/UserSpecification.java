package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.User;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class UserSpecification {
    public static Specification<User> hasLocation(List<String> location) {
       return (root, query, cb) -> {
            Expression<String> locationExpression = root.get("hopeLocation");
            Predicate[] predicates = location.stream()
                    .map(part -> cb.like(locationExpression, "%" + part + "%"))
                    .toArray(Predicate[]::new);
            return cb.or(predicates);
        };
    }

    public static Specification<User> hasJob(List<String> job) {
        return (root, query, cb) -> {
            Expression<String> jobExpression = root.get("hopeJob");
            Predicate[] predicates = job.stream()
                    .map(part -> cb.like(jobExpression, "%" + part + "%"))
                    .toArray(Predicate[]::new);
            return cb.or(predicates);
        };
    }

    public static Specification<User> isGender(List<Boolean> gender) {
        return (root, query, cb) -> root.get("gender").in(gender);
    }

    public static Specification<User> overAge(Date birth) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("birth"), birth);
    }
}
