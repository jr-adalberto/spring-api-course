package com.spring.course.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring.course.domain.User;
import com.spring.course.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @Ignore
    public void AsaveTest() {
        User user = new User(null, "Maria", "maria@gmail.com", "456", Role.SIMPLE, null, null);
        User createdUser = userRepository.save(user);

        assertThat(createdUser.getId()).isEqualTo(1L);

    }

    @Ignore
    public void updateTest() {
        User user = new User(11L, "Maria", "maria@gmail.com", "456", Role.SIMPLE, null, null);
        User updatedUser = userRepository.save(user);

        assertThat(updatedUser.getName()).isEqualTo("Maria Silva");
    }

    @Ignore
    public void getByIdTest() {
        Optional<User> result = userRepository.findById(11L);
        User user = result.get();

        assertThat(user.getPassword()).isEqualTo("456");
    }

    @Ignore
    public void listTest() {
        List<User> users = userRepository.findAll();

        assertThat(users.size()).isEqualTo(1);
    }

    @Ignore
    public void loginTest() {
        Optional<User> result = userRepository.login("maria@gmail.com", "456");
        User loggedUser = result.get();

        assertThat(loggedUser.getId()).isEqualTo(1L);
    }

    @Test
    public void updateRoleTest() {
        int affectedRows = userRepository.updateRole(7L, Role.ADMINISTRATOR);
        assertThat(affectedRows).isEqualTo(1);
    }

}