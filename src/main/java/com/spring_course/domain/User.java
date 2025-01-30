package com.spring_course.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring_course.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 75, nullable = false)
    private String name;

    @Column(length = 75, nullable = false, unique = true)
    private String email;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<Request> requests = new ArrayList<Request>();

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    private List<RequestStage> stages = new ArrayList<RequestStage>();

    @Version
    @Column(nullable = false)
    private Long version;


    public User() {
    }

    public User(Long id, Long version, List<RequestStage> stages, List<Request> requests, Role role, String password, String email, String name) {
        this.id = id;
        this.version = version;
        this.stages = stages;
        this.requests = requests;
        this.role = role;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public User(Object o, String maria, String mail, String number, Role role, Object o1, Object o2) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public List<RequestStage> getStages() {
        return stages;
    }

    public void setStages(List<RequestStage> stages) {
        this.stages = stages;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}