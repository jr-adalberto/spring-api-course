package com.spring_course.service;

import com.spring_course.domain.Request;
import com.spring_course.enums.RequestState;
import com.spring_course.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestService {

    @Autowired
    private RequestRepository requestRepository;

    public Request save(Request request) {
        request.setState(RequestState.OPEN);
        request.setCreationDate(new Date());
        Request createdRequest = requestRepository.save(request);
        return createdRequest;
    }

    public Request update(Request request) {
        Request updatedRequest = requestRepository.save(request);
        return updatedRequest;
    }

    public Request getById(Long id) {
        Optional<Request> result = requestRepository.findById(id);
        return result.get();
    }

    public List<Request> listAll() {
        List<Request> requests = requestRepository.findAll();
        return requests;
    }

    public List<Request> listAllByOwnerId(Long ownerId) {
        List<Request> requests = requestRepository.findAllByOwnerId(ownerId);
        return requests;
    }
}
