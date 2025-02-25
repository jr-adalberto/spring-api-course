package com.spring.course.resource;

import com.spring.course.domain.User;
import com.spring.course.dto.*;
import com.spring.course.model.PageModel;
import com.spring.course.model.PageRequestModel;
import com.spring.course.security.AccessManager;
import com.spring.course.security.JwtManager;
import com.spring.course.service.RequestService;
import com.spring.course.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "users")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;
    private final RequestService requestService;
    private final AuthenticationManager authManager;
    private final JwtManager jwtManager;
    private final AccessManager accessManager;

    @Secured({ "ROLE_ADMINISTRATOR" })
    @PostMapping
    public ResponseEntity<User> save(@RequestBody @Valid UserSavedto userdto) {
        User userToSave = userdto.transformToUser();
        User createdUser = userService.save(userToSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @PreAuthorize("@accessManager.isOwner(#id)")
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
            @RequestParam Map<String, String> params) {

        PageRequestModel pr = new PageRequestModel(params);
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
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            System.out.println("Email: " + email);
            System.out.println("Roles: " + roles);

            UserLoginResponsedto response = jwtManager.createToken(email, roles);

            System.out.println("Generated JWT Token: " + response.getToken());

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials: " + e.getMessage());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new UserLoginResponsedto(null, null, null));
        }
    }


    @Secured({ "ROLE_ADMINISTRATOR" })
    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdateRoledto userdto) {
        User user = new User();
        user.setId(id);
        user.setRole(userdto.getRole());

        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        userService.delete(id);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User deleted successfully");
        response.put("id", id.toString());

        return ResponseEntity.ok(response);
    }
}
