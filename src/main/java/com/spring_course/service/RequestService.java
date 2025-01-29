package com.spring_course.service;

import com.spring_course.domain.Request;
import com.spring_course.domain.User;
import com.spring_course.enums.RequestState;
import com.spring_course.exception.NotFoundException;
import com.spring_course.model.PageModel;
import com.spring_course.model.PageRequestModel;
import com.spring_course.repository.RequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        return result.orElseThrow(() -> new NotFoundException("There are not request with id = " + id));
    }

    public List<Request> listAll() {
        List<Request> requests = requestRepository.findAll();
        return requests;
    }

    public PageModel<Request> listAllOnLazyMode(PageRequestModel pr) {
        Pageable pageable = PageRequest.of(pr.toSpringPageRequest().getPageNumber(), pr.toSpringPageRequest().getPageSize());

        Page<Request> page = requestRepository.findAll(pageable);

        PageModel<Request> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }

    public List<Request> listAllByOwnerId(Long ownerId) {
        List<Request> requests = requestRepository.findAllByOwnerId(ownerId);
        return requests;
    }

    public PageModel<Request> listAllByOwnerIdOnLazyModel(Long ownerId, PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);

        PageModel<Request> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }

}
