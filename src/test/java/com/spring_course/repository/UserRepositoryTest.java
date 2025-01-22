package com.spring_course.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.spring_course.domain.User;
import com.spring_course.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User savedUser;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        User user = new User(null, "Joao", "joao.reis@gmail.com", "123", Role.ADMINISTRATOR, null, null);
        savedUser = userRepository.save(user);
    }

    @Test
    public void AsaveTest() {
        User user = new User(null, "Maria", "maria@gmail.com", "456", Role.SIMPLE, null, null);
        User createdUser = userRepository.save(user);

        assertThat(createdUser.getId()).isNotNull();
    }

    @Test
    public void updateTest() {
        User user = userRepository.findById(savedUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        user.setName("Joao Atualizado");
        User updatedUser = userRepository.save(user);

        assertThat(updatedUser.getName()).isEqualTo("Joao Atualizado");
    }

    @Test
    public void getByIdTest() {
        Optional<User> result = userRepository.findById(savedUser.getId());
        User user = result.orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        assertThat(user.getPassword()).isEqualTo("123");
    }


    @Test
    public void listTest() {
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(1);
    }

    @Test
    public void loginTest() {
        User loggedUser = userRepository.login("joao.reis@gmail.com", "123")
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado ou credenciais inválidas"));

        assertThat(loggedUser.getId()).isNotNull();
    }
}
