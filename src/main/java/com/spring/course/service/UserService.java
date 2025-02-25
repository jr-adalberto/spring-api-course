package com.spring.course.service;

import com.spring.course.domain.User;
import com.spring.course.exception.NotFoundException;
import com.spring.course.model.PageModel;
import com.spring.course.security.CustomPasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.spring.course.model.PageRequestModel;
import com.spring.course.repository.UserRepository;
import com.spring.course.service.util.HashUtil;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CustomPasswordEncoder passwordEncoder;

    public User save(User user) {
        String hash = HashUtil.getSecureHash(user.getPassword());
        user.setPassword(hash);

        User createdUser = userRepository.save(user);
        return createdUser;
    }

    @Transactional
    public User update(User user) {
        Optional<User> existingUserOpt = userRepository.findById(user.getId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();

            existingUser.setName(user.getName());
            existingUser.setEmail(user.getEmail());

            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                existingUser.setPassword(HashUtil.getSecureHash(user.getPassword()));
            }

            return userRepository.save(existingUser);
        }
        throw new EntityNotFoundException("User not found with ID: " + user.getId());
    }

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public PageModel<User> listAllOnLazyMode(PageRequestModel pageRequestModel) {
        Pageable pageable = PageRequest.of(pageRequestModel.getPage(), pageRequestModel.getSize());
        Page<User> page = userRepository.findAll(pageable);
        return new PageModel<>(
                (int) page.getTotalElements(),
                page.getSize(),
                page.getTotalPages(),
                page.getContent()
        );
    }


    public User login(String email, String password) {
        password = HashUtil.getSecureHash(password);
        Optional<User> result = userRepository.login(email, password);

        return result.orElseThrow(() -> new NotFoundException("Invalid email or password"));
    }

    public int updateRole(User user) {
        return userRepository.updateRole(user.getId(), user.getRole());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> result = userRepository.findByEmail(username);

        if(result.isEmpty()) throw new UsernameNotFoundException("Dosen't exist user with email = " + username);

        User user = result.get();

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), authorities);
    }

    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with ID: " + id));
    }

    @Transactional
    public void delete(Long id) {
        Optional<User> userOpt = userRepository.findById(id);
        if (userOpt.isPresent()) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User not found with ID: " + id);
        }
    }
}
