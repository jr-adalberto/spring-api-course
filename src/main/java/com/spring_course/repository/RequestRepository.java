package com.spring_course.repository;

import com.spring_course.domain.Request;
import com.spring_course.enums.RequestState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    public List<Request> findAllByOwnerId(Long id);

    @Query("UPDATE request SET state = ?2 WHERE id = ?1")
    public Request updateStatus(Long id, RequestState state);

}
