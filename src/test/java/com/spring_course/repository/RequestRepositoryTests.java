package com.spring_course.repository;

import com.spring_course.domain.Request;
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
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
public class RequestRepositoryTests {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;


    @BeforeEach
    public void setUp() {

        User owner = new User();
        owner.setName("Test User");
        owner.setEmail("testuser@example.com");
        owner.setPassword("password");
        owner.setRole(Role.SIMPLE);
        userRepository.save(owner);

        Request request = new Request();
        request.setSubject("Novo Laptop HP");
        request.setDescription("Pretendo obter um laptop HP");
        request.setCreationDate(new Date());
        request.setState(RequestState.OPEN);
        request.setOwner(owner);
        requestRepository.save(request);
    }


    @Test
    public void AsaveTest() {

        User owner = new User();
        owner.setName("Test User");
        owner.setEmail("testuser@example.com");
        owner.setPassword("password");
        owner.setRole(Role.SIMPLE);
        userRepository.save(owner);


        Request request = new Request();
        request.setSubject("Novo Laptop HP");
        request.setDescription("Pretendo obter um laptop HP");
        request.setCreationDate(new Date());
        request.setState(RequestState.OPEN);
        request.setOwner(owner);
        Request createdRequest = requestRepository.save(request);

        assertThat(createdRequest.getId()).isNotNull();
    }


    @Test
    public void updateTest() {

        Optional<Request> result = requestRepository.findById(1L);
        assertThat(result).isPresent();


        Request request = result.get();
        request.setDescription("Pretendo obter um laptop HP, de RAM 16GB");
        Request updatedRequest = requestRepository.save(request);

        assertThat(updatedRequest.getDescription()).isEqualTo("Pretendo obter um laptop HP, de RAM 16GB");
    }


    @Test
    public void getByIdTest() {
        Optional<Request> result = requestRepository.findById(1L);
        assertThat(result).isPresent();
        Request request = result.get();
        assertThat(request.getSubject()).isEqualTo("Novo Laptop HP");
    }


    @Test
    public void listTest() {
        List<Request> requests = requestRepository.findAll();
        assertThat(requests.size()).isEqualTo(1);
    }


    @Test
    public void listByOwnerIdTest() {
        List<Request> requests = requestRepository.findAllByOwnerId(1L);
        assertThat(requests.size()).isEqualTo(1);
    }

    @Test
    public void updateStatusTest(){
        int affectedRows = requestRepository.updateStatus(1L, RequestState.IN_PROGRESS);
        assertThat(affectedRows).isEqualTo(1);
    }


}
