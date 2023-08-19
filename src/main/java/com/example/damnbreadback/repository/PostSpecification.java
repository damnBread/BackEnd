package com.example.damnbreadback.repository;

import com.example.damnbreadback.entity.Post;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PostSpecification {
    public static Specification<Post> hasLocation(List<String> location) {
        return (root, query, cb) -> {
            Expression locationExpression = root.get("location");
            Predicate[] predicates = location.stream()
                    .map(part->cb.like(locationExpression, "%" + part + "%"))
                    .toArray(Predicate[]::new);
            return cb.or(predicates);
        };
    }

    public static Specification<Post> hasJob(List<String> job) {
        return (root, query, cb) -> {
            Expression jobExpression = root.get("job");
            Predicate[] predicates = job.stream()
                    .map(part->cb.like(jobExpression, "%" + part + "%"))
                    .toArray(Predicate[]::new);
            return cb.or(predicates);
        };
    }


    public static Specification<Post> hasGender(Boolean genderLimit) {
        return (root, query, cb) -> root.get("genderLimit").in(genderLimit);
    }
    public static Specification<Post> hasGender2(Boolean isFree) {
        if(isFree)
            return (root, query, cb) -> root.get("genderLimit").isNotNull();
        else return (root, query, cb) -> root.get("genderLimit").isNull();
    }

    public static Specification<Post> overAge(int minAge) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("ageMin"), minAge);
    }

    public static Specification<Post> overHourPay(int hourPay) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("hourPay"), hourPay);
    }

    public static Specification<Post> hasPayMethod(Boolean payMethod) {
        return (root, query, cb) -> root.get("payMethod").in(payMethod);
    }

    public static Specification<Post> hasDayOfWeek(List<Integer> dayOfWeek) {
        return (root, query, cb) -> root.get("workDay").in(dayOfWeek);
    }

    public static Specification<Post> hasWorkTime(int start, int end) {
        return (root, query, db) -> db.between(root.get("workStart"), start, end);
    }


}
