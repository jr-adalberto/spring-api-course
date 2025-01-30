package com.spring.course.service;

import com.spring.course.domain.User;
import com.spring.course.exception.DuplicateUserException;
import com.spring.course.exception.NotFoundException;
import com.spring.course.model.PageModel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.spring.course.model.PageRequestModel;
import com.spring.course.repository.UserRepository;
import com.spring.course.service.util.HashUtil;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new DuplicateUserException("There is already a registered user with the email: " + user.getEmail());
        }

        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);

        return userRepository.save(user);
    }

    public User update(User user) {
        Optional<User> existingUserOpt = userRepository.findById(user.getId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            if (user.getName() != null) {
                existingUser.setName(user.getName());
            }
            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(HashUtil.getSecureHash(user.getPassword()));
            }

            User updatedUser = userRepository.save(existingUser);
            return updatedUser;
        }
        throw new EntityNotFoundException("User not found with ID: " + user.getId());
    }

    public User getById(Long id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElseThrow(() -> new NotFoundException("There are not user with id = " + id));
    }

    public List<User> listAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public PageModel<User> listAllOnLazyMode(PageRequestModel pr) {
        Pageable pageable = PageRequest.of(pr.toSpringPageRequest().getPageNumber(), pr.toSpringPageRequest().getPageSize());

        Page<User> page = userRepository.findAll(pageable);

        PageModel<User> pm = new PageModel<>((int) page.getTotalElements(), page.getSize(), page.getTotalPages(), page.getContent());
        return pm;
    }
    public User login(String email, String password) {
        password = HashUtil.getSecureHash(password);
        Optional<User> result = userRepository.login(email, password);

        return result.orElseThrow(() -> new NotFoundException("Invalid email or password"));
    }

    public int updateRole(User user) {
        return userRepository.updateRole(user.getId(), user.getRole());
    }
}
