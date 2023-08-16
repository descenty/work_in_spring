package com.descenty.work_in_spring.entity.user.resume;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "education")
@Getter
@Setter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String university;
    private String faculty;
    private String speciality;
    private String degree;
    private String yearOfGraduation;
}