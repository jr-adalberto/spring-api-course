package com.spring.course.service;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.domain.User;
import com.spring.course.exception.NotFoundException;
import com.spring.course.model.PageModel;
import com.spring.course.model.PageRequestModel;
import com.spring.course.repository.RequestRepository;
import com.spring.course.repository.RequestStageRepository;
import com.spring.course.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestStageService {


    private final RequestStageRepository requestStageRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public RequestStage save(RequestStage stage) {
        User owner = userRepository.findById(stage.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Owner cannot find this id : " + stage.getOwner().getId()));
        Request request = requestRepository.findById(stage.getRequest().getId())
                .orElseThrow(() -> new RuntimeException("Request cannot find this id: " + stage.getRequest().getId()));

        stage.setOwner(owner);
        stage.setRequest(request);
        return requestStageRepository.save(stage);
    }

    public RequestStage getById(Long id) {
        Optional<RequestStage> result = requestStageRepository.findById(id);
        return result.orElseThrow(() -> new NotFoundException("There are not request stage with id = " + id));
    }

    public List<RequestStage> listAllByRequestId(Long requestId) {
        List<RequestStage> stages = requestStageRepository.findAllByRequestId(requestId);
        return stages;
    }

    public PageModel<RequestStage> listAllByRequestIdOnLazyModel(Long requestId, PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<RequestStage> page = requestStageRepository.findAllByRequestId(requestId, pageable);

        PageModel<RequestStage> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }

}
