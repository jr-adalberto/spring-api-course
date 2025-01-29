package com.spring_course.service;

import com.spring_course.domain.Request;
import com.spring_course.domain.RequestStage;
import com.spring_course.domain.User;
import com.spring_course.enums.RequestState;
import com.spring_course.exception.NotFoundException;
import com.spring_course.repository.RequestRepository;
import com.spring_course.repository.RequestStageRepository;
import com.spring_course.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RequestStageService {

    @Autowired
    private RequestStageRepository requestStageRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;


    public RequestStage save(RequestStage stage) {
        User owner = userRepository.findById(stage.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner não encontrado com id: " + stage.getOwner().getId()));
        Request request = requestRepository.findById(stage.getRequest().getId())
                .orElseThrow(() -> new RuntimeException("Request não encontrado com id: " + stage.getRequest().getId()));

        stage.setOwner(owner);
        stage.setRequest(request);
        return requestStageRepository.save(stage);
    }

    public RequestStage getById(Long id) {
        Optional<RequestStage> result = requestStageRepository.findById(id);
        return result.orElseThrow(()-> new NotFoundException("There are not request stage with id = " + id));
    }

    public List<RequestStage> listAllByRequestId(Long requestId) {
        List<RequestStage> stages = requestStageRepository.findAllByRequestId(requestId);
        return stages;
    }


}
