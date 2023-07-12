package com.descenty.work_in_spring.entity;

import com.descenty.work_in_spring.entity.resume.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vacancy_response")
@Getter
@Setter
public class UserResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id")
    private Vacancy vacancy;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume resume;
    private String coverLetter;
    @Enumerated(EnumType.STRING)
    private UserResponseStatus status;
}
