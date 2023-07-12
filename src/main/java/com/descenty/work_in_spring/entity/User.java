package com.descenty.work_in_spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

// User entity for Spring Security
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
}
