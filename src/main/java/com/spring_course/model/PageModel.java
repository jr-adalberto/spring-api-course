package com.spring_course.model;

import com.spring_course.domain.User;

import java.io.Serializable;
import java.util.List;

public class PageModel<T> implements Serializable {
    private int totalElements;
    private int pageSize;
    private int totalPages;
    private List<T> elements;

    public PageModel() {
    }

    public PageModel(int totalElements, int pageSize, int totalPages, List<T> elements) {
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.totalPages = totalPages;
        this.elements = elements;
    }


    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}