package com.spring.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@AllArgsConstructor
public class UserLoginResponsedto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String token;
    private Long expireIn;
    private String tokenProvider;

}