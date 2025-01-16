package com.spring_course.repository;

import com.spring_course.domain.RequestStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestStageRepository extends JpaRepository<RequestStage, Long> {
    List<RequestStage> findByRequestId(Long requestId);
}
