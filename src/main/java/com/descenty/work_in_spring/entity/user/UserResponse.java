package com.descenty.work_in_spring.entity.user;

import com.descenty.work_in_spring.entity.Vacancy;
import com.descenty.work_in_spring.entity.user.resume.Resume;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_response")
@Getter
@Setter
public class UserResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", insertable = false, updatable = false)
    private Vacancy vacancy;
    @Column(name = "vacancy_id", nullable = false)
    private String vacancyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", insertable = false, updatable = false)
    private Resume resume;
    @Column(name = "resume_id", nullable = false)
    private String resumeId;

    private String coverLetter;
    @Enumerated(EnumType.STRING)
    private UserResponseStatus status;
}
