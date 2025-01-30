package com.spring_course.dto;

import com.spring_course.enums.Role;

import javax.validation.constraints.NotNull;

public class UserUpdateRoledto {

    @NotNull(message = "Role required")
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
