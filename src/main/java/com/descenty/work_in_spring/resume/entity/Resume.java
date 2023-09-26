package com.descenty.work_in_spring.resume.entity;

import com.descenty.work_in_spring.user.entity.VacancyResponse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "resume")
@Getter
@Setter
public class Resume {
    public enum ModerationStatus {
        PENDING, ACCEPTED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String description;

    private Integer salary;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VacancyResponse> vacancyResponses;

    @Column(name = "moderation_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus = ModerationStatus.PENDING;
}
