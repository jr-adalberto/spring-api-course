package com.spring.course.service;

import com.spring.course.domain.Request;
import com.spring.course.domain.User;
import com.spring.course.enums.RequestState;
import com.spring.course.exception.NotFoundException;
import com.spring.course.model.PageModel;
import com.spring.course.model.PageRequestModel;
import com.spring.course.repository.RequestRepository;
import com.spring.course.repository.UserRepository;
import com.spring.course.specification.RequestSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {


    private final RequestRepository requestRepository;
    private final UserRepository userRepository;

    public Request save(Request request) {
        User owner = userRepository.findById(request.getOwner().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + request.getOwner().getId()));

        request.setOwner(owner);
        request.setState(RequestState.OPEN);
        request.setCreationDate(new Date());

        return requestRepository.save(request);
    }

    public Request update(Request request) {
        User owner = userRepository.findById(request.getOwner().getId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + request.getOwner().getId()));

        request.setOwner(owner);

        return requestRepository.save(request);
    }

    public Request getById(Long id) {
        Optional<Request> result = requestRepository.findById(id);
        return result.orElseThrow(() -> new NotFoundException("There are not request with id = " + id));
    }

    public List<Request> listAll() {
        return requestRepository.findAll();
    }

    public PageModel<Request> listAllOnLazyMode(PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();

        Specification<Request> spec = RequestSpecification.search(pr.getSearch());

        Page<Request> page = requestRepository.findAll(spec, pageable);

        return new PageModel<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent()
        );
    }

    public List<Request> listAllByOwnerId(Long ownerId) {
        return requestRepository.findAllByOwnerId(ownerId);
    }

    public PageModel<Request> listAllByOwnerIdOnLazyModel(Long ownerId, PageRequestModel pr) {
        Pageable pageable = pr.toSpringPageRequest();
        Page<Request> page = requestRepository.findAllByOwnerId(ownerId, pageable);

        return new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
    }

    @Transactional
    public void delete(Long id) {
        Optional<Request> requestOpt = requestRepository.findById(id);
        if (requestOpt.isPresent()) {
            requestRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Request not found with ID: " + id);
        }
    }

}
