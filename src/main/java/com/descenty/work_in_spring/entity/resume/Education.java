package com.descenty.work_in_spring.entity.resume;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "education")
@Getter
@Setter
public class Education {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String university;
    private String faculty;
    private String speciality;
    private String degree;
    private String yearOfGraduation;
}