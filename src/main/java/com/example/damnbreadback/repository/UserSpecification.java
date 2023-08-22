package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Career;
import com.example.damnbreadback.entity.User;
import jakarta.persistence.criteria.*;
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

    public static Specification<User> overCareer(int career){
        return (root, query, criteriaBuilder) -> {
//            Join<Career, User> careerUserJoin = root.join("career");
//            return criteriaBuilder.equal(careerUserJoin.get(""), career);
//            Join<User, Career> careerUserJoin = root.join("career");

            // Create a subquery to calculate the sum of 'period' grouped by 'id'
            Subquery<Integer> subquery = query.subquery(Integer.class);
            Root<Career> subqueryRoot = subquery.from(Career.class);
            subquery.select(criteriaBuilder.sum(subqueryRoot.get("period")));
            subquery.groupBy(subqueryRoot.get("id"));

            // Use a correlated subquery to relate 'User' to the subquery
            Predicate correlatedPredicate = criteriaBuilder.equal(root, subqueryRoot.get("user"));
            subquery.where(correlatedPredicate);

            // Create the main query to check if the sum is greater than 'career'
            Predicate overCareerPredicate = criteriaBuilder.greaterThanOrEqualTo(subquery, career);

            return overCareerPredicate;
        };
    }
}
