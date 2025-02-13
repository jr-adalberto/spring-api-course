package com.spring.course.resource;

import com.spring.course.constant.SecurityConstants;
import com.spring.course.domain.Request;
import com.spring.course.domain.User;
import com.spring.course.dto.*;
import com.spring.course.model.PageModel;
import com.spring.course.model.PageRequestModel;
import com.spring.course.security.JwtManager;
import com.spring.course.service.RequestService;
import com.spring.course.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;
    private final RequestService requestService;
    private final AuthenticationManager authManager;
    private final JwtManager jwtManager;

    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSavedto userdto) {
        User userToSave = userdto.transformToUser();
        User createdUser = userService.save(userToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdatedto userdto) {
        User user = userdto.transformToUser();
        user.setId(id);
        User updatedUser = userService.update(user);
        return ResponseEntity.ok(updatedUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        User user = userService.getById(id);
        return ResponseEntity.ok(user);
    }

    @GetMapping
    public ResponseEntity<PageModel<User>> listAll(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<User> pm = userService.listAllOnLazyMode(pr);

        return ResponseEntity.ok(pm);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponsedto> login(@RequestBody @Valid UserLogindto user) {
        try {

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword());
            Authentication auth = authManager.authenticate(token);

            SecurityContextHolder.getContext().setAuthentication(auth);

            org.springframework.security.core.userdetails.User userSpring =
                    (org.springframework.security.core.userdetails.User) auth.getPrincipal();

            String email = userSpring.getUsername();
            List<String> roles = userSpring.getAuthorities()
                    .stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList());

            String jwtToken = jwtManager.createToken(
                    email,
                    roles,
                    SecurityConstants.API_KEY,
                    SecurityConstants.JWT_EXP_DAYS,
                    SecurityConstants.JWT_PROVIDER
            );

            UserLoginResponsedto response = new UserLoginResponsedto();
            response.setToken(jwtToken);
            response.setEmail(email);
            response.setRoles(roles);

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new UserLoginResponsedto(null, null, null, "Non-existent user or invalid password", null));
        }
    }

    @GetMapping("/{id}/requests")
    public ResponseEntity<PageModel<Request>> listAllRequestsById(
            @PathVariable(name = "id") Long id,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "page", defaultValue = "0") int page) {

        PageRequestModel pr = new PageRequestModel(page, size);
        PageModel<Request> pm = requestService.listAllByOwnerIdOnLazyModel(id, pr);

        return ResponseEntity.ok(pm);
    }


    @Secured({"ROLE_ADMINISTRATOR"})
    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdateRoledto userdto) {
        User user = new User();
        user.setId(id);
        user.setRole(userdto.getRole());

        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }
}
