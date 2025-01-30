package com.spring.course.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserLogindto {

    @Email(message = "Invalid email address")
    private String email;

    @NotBlank(message = "Password required")
    private String password;

}
