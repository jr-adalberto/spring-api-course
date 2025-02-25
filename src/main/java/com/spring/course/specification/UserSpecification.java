package com.spring.course.specification;

import com.spring.course.domain.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class UserSpecification {

    public static Specification<User> search(String text) {
        return new Specification<User>() {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (text == null || text.trim().isEmpty()) return null;

                String likeTerm = "%" + text + "%";

                return cb.or(cb.like(root.get("name"), likeTerm),
                       cb.or(cb.like(root.get("email"), likeTerm),
                       cb.like(root.get("role").as(String.class),
                       likeTerm)));

            }

        };
    }

}