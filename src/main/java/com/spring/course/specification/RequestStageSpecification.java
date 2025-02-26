package com.spring.course.specification;

import com.spring.course.domain.RequestStage;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.io.Serial;

public class RequestStageSpecification {

    public static Specification<RequestStage> search(String text) {
        return new Specification<RequestStage>() {

            @Serial
            private static final long serialVersionUID = 1L;

            @Override
            public Predicate toPredicate(Root<RequestStage> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if (text == null || text.trim().isEmpty()) return null;

                String likeTerm = "%" + text + "%";

                return cb.or(
                        cb.like(root.get("description"), likeTerm),
                        cb.like(root.get("state").as(String.class), likeTerm)
                );
            }
        };
    }
}