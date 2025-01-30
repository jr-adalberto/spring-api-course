package com.spring_course.repository;

import com.spring_course.domain.User;
import com.spring_course.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM user u WHERE email = ?1 AND password = ?2")
    public Optional<User> login(String email, String password);

    @Transactional(readOnly = false)
    @Modifying
    @Query("UPDATE user SET role = ?2 WHERE id = ?1")
    public int updateRole(Long id, Role role);

    public Optional<User> findByEmail(String email);

}