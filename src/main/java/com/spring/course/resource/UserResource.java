package com.spring.course.resource;

import com.spring.course.domain.Request;
import com.spring.course.domain.User;
import com.spring.course.dto.UserLogindto;
import com.spring.course.dto.UserSavedto;
import com.spring.course.dto.UserUpdateRoledto;
import com.spring.course.dto.UserUpdatedto;
import com.spring.course.model.PageModel;
import com.spring.course.model.PageRequestModel;
import com.spring.course.service.RequestService;
import com.spring.course.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "users")
public class UserResource {

    private final UserService userService;
    private final RequestService requestService;

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
    public ResponseEntity<User> login(@RequestBody @Valid UserLogindto user) {
        User loggedUser = userService.login(user.getEmail(), user.getPassword());
        return ResponseEntity.ok(loggedUser);
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

    @PatchMapping("/role/{id}")
    public ResponseEntity<?> updateRole(@PathVariable(name = "id") Long id, @RequestBody @Valid UserUpdateRoledto userdto) {
        User user = new User();
        user.setId(id);
        user.setRole(userdto.getRole());

        userService.updateRole(user);

        return ResponseEntity.ok().build();
    }
}
