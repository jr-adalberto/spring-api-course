package com.spring_course.specification;

import com.spring_course.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> search(String text) {
        return (Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (text == null || text.trim().isEmpty()) {
                return null;
            }

            String likeTerm = "%" + text + "%";


            return cb.or(
                    cb.like(root.get("name"), likeTerm),
                    cb.like(root.get("email"), likeTerm),
                    cb.like(root.get("role").as(String.class), likeTerm)
            );
        };
    }
}