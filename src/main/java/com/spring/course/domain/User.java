package com.spring.course.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.spring.course.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -4006835110179755444L;

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
    @Column(nullable = false, updatable = false)
    private Long version;

    public User(Long id, String name, String email, String password, Role role, List<Request> requests, List<RequestStage> stages) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.requests = requests;
        this.stages = stages;
    }

    public User(Long id, String name, String email, String password, Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}