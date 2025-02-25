package com.spring.course.dto;

import com.spring.course.domain.User;
import com.spring.course.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSavedto {

    @NotBlank(message = "Name cannot be empty")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 7, max = 99, message = "Password must be between 7 and 99 characters")
    private String password;

    @NotNull(message = "Role is required")
    private Role role;

    // Removido os campos requests e stages do DTO
    // Eles não devem ser enviados na criação de um novo usuário

    public User transformToUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password); // A senha será hasheada no serviço
        user.setRole(this.role);
        // Requests e stages não são definidos no DTO, pois são gerenciados separadamente
        return user;
    }
}