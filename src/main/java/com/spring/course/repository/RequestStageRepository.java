package com.spring.course.repository;

import com.spring.course.domain.RequestStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {

    public List<RequestStage> findAllByRequestId(Long id);

    public Page<RequestStage> findAllByRequestId(Long id, Pageable pageable);

    Page<RequestStage> findAllByRequestId(Long requestId, Pageable pageable, Specification<RequestStage> spec);
}