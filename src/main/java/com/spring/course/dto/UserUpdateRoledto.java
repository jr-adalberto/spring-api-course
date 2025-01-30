package com.spring.course.dto;

import com.spring.course.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateRoledto {

    @NotNull(message = "Role required")
    private Role role;

}
