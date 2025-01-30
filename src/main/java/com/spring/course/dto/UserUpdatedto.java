package com.spring.course.dto;

import com.spring.course.domain.Request;
import com.spring.course.domain.RequestStage;
import com.spring.course.domain.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatedto {

    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Invalid email address")
    private String email;

    @Size(min = 7, max = 99, message = "Password must be between 7 and 99")
    private String password;

    private List<Request> requests = new ArrayList<Request>();
    private List<RequestStage> stages = new ArrayList<RequestStage>();

    public User transformToUser() {
        User user = new User(null, this.name, this.email, this.password, null, this.requests, this.stages);
        return user;
    }
}
