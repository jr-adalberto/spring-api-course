package com.spring_course.repository;

import com.spring_course.domain.RequestStage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {
    List<RequestStage> findAllByRequestId(Long requestId);
    public Page<RequestStage> findAllByRequestId(Long id, Pageable pageable);
}
