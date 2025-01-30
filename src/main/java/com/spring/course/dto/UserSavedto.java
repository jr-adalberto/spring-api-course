package com.spring.course.dto;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.domain.User;
import com.spring.course.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSavedto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email
    private String email;

    @Size(min = 7, max = 99, message = "Password must be between 7 and 99")
    private String password;

    @NotNull(message = "Role required")
    private Role role;

    private List<Request> requests = new ArrayList<>();
    private List<RequestStage> stages = new ArrayList<>();

    public User transformToUser() {
        User user = new User(null, this.name, this.email, this.password, this.role);
        return user;
    }

}
