package com.spring.course.specification;

import com.spring.course.domain.Request;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class RequestSpecification {

    public static Specification<Request> search(String text) {
        return new Specification<Request>() {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<Request> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (text == null || text.trim().isEmpty()) return null;

                String likeTerm = "%" + text + "%";

                return cb.or(
                        cb.like(root.get("subject"), likeTerm),
                        cb.like(root.get("description"), likeTerm),
                        cb.like(root.get("state").as(String.class), likeTerm)
                );
            }
        };
    }
}