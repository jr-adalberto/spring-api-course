package com.spring.course.resource.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorList extends ApiError {

    @Serial
    private static final long serialVersionUID = -1677862535400134851L;

    private List<String> errors;

}