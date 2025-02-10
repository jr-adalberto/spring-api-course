package com.spring.course.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponsedto implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String token;
    private Long expireIn;
    private String tokenProvider;

}