package com.spring_course.repository;

import com.spring_course.domain.Request;
import com.spring_course.domain.RequestStage;
import com.spring_course.domain.User;
import com.spring_course.enums.RequestState;
import com.spring_course.enums.Role;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SpringBootTest
@RunWith(SpringRunner.class)
public class RequestStageTests {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestRepository requestRepository;

    @BeforeEach
    void setup() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setName("Test User");
        user.setPassword("password");
        user.setRole(Role.ADMINISTRATOR);
        userRepository.save(user);

        Request request = new Request();
        request.setSubject("Test Subject");
        request.setDescription("Test Description");
        request.setState(RequestState.OPEN);
        request.setOwner(user);
        requestRepository.save(request);
    }


    @Test
    public void AsaveTest() {
        User owner = userRepository.findById(6L).orElseThrow(() -> new RuntimeException("Owner not found"));
        Request request = requestRepository.findById(1L).orElseThrow(() -> new RuntimeException("Request not found"));

        RequestStage stage = new RequestStage();
        stage.setDescription("Testando a criação de um novo estágio");
        stage.setRealizationDate(new Date());
        stage.setState(RequestState.OPEN);
        stage.setRequest(request);
        stage.setOwner(owner);
        RequestStage createdStage = requestStageRepository.save(stage);

        assertThat(createdStage.getId()).isNotNull();
    }


    @Test
    public void getByIdTest() {
        List<RequestStage> stages = requestStageRepository.findAll();
        assertThat(stages).isNotEmpty();

        RequestStage stage = stages.get(0);
        assertThat(stage.getDescription()).isEqualTo("Testando a criação de um novo estágio");
    }


    @Test
    public void listByRequestIdTest() {
        Request request = requestRepository.findBySubject("Novo Laptop HP")
                .orElseThrow(() -> new RuntimeException("Request not found"));

        List<RequestStage> stages = requestStageRepository.findAllByRequestId(request.getId());
        assertThat(stages.size()).isGreaterThan(0);
    }

}
