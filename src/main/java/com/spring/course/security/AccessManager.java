package com.spring.course.security;

import com.spring.course.domain.Request;
import com.spring.course.domain.User;
import com.spring.course.exception.NotFoundException;
import com.spring.course.repository.UserRepository;
import com.spring.course.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("accessManager")
@RequiredArgsConstructor
public class AccessManager {

    private final UserRepository userRepository;

    private final RequestService requestService;

    public boolean isOwner(Long id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> result = userRepository.findByEmail(email);

        if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);

        User user = result.get();

        return user.getId() == id;
    }

    public boolean isRequestOwner(Long id) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> result = userRepository.findByEmail(email);

        if(!result.isPresent()) throw new NotFoundException("There are not user with email = " + email);

        User user = result.get();

        Request request = requestService.getById(id);

        return user.getId() == request.getOwner().getId();

    }

}