package com.spring_course.service;

import com.spring_course.domain.User;
import com.spring_course.exception.NotFoundException;
import com.spring_course.repository.UserRepository;
import com.spring_course.service.util.HashUtil;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User save(User user) {
        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);
        User createdUser = userRepository.save(user);
        return createdUser;
    }

    public User update(User user) {
        Optional<User> existingUserOpt = userRepository.findById(user.getId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            existingUser.setPassword(HashUtil.getSecureHash(user.getPassword()));

            User updatedUser = userRepository.save(existingUser);
            return updatedUser;
        }
        throw new EntityNotFoundException("User not found with ID: " + user.getId());
    }


    public User getById(Long id) {
        Optional<User> result = userRepository.findById(id);
        return result.orElseThrow(()-> new NotFoundException("There are not user with id = " + id));
    }

    public List<User> listAll() {
        List<User> users = userRepository.findAll();
        return users;
    }

    public User login(String email, String password) {
        password = HashUtil.getSecureHash(password);
        Optional<User> result = userRepository.login(email, password);
        return result.get();
    }
}
