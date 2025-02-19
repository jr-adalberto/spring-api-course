package com.spring.course.repository;

import com.spring.course.domain.Request;
import com.spring.course.enums.RequestState;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    public List<Request> findAllByOwnerId(Long id);

    public Page<Request> findAllByOwnerId(Long id, Pageable pageable);

    @Transactional(readOnly = false)
    @Modifying
    @Query("UPDATE request SET state = ?2 WHERE id = ?1")
    public int updateStatus(Long id, RequestState state);
}