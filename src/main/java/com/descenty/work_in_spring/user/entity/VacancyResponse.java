package com.descenty.work_in_spring.user.entity;

import com.descenty.work_in_spring.resume.entity.Resume;
import com.descenty.work_in_spring.vacancy.Vacancy;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "vacancy_response")
@Getter
@Setter
public class VacancyResponse {
    public enum Status {
        PENDING, ACCEPTED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vacancy_id", insertable = false, updatable = false)
    private Vacancy vacancy;
    @Column(name = "vacancy_id", nullable = false)
    private UUID vacancyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id", insertable = false, updatable = false)
    private Resume resume;
    @Column(name = "resume_id", nullable = false)
    private UUID resumeId;

    private String coverLetter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
}
