package com.spring.course.model;

import com.spring.course.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class PageModel<T> implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int totalElements;
    private int pageSize;
    private int totalPages;
    private List<T> elements;
}