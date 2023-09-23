package com.descenty.work_in_spring.resume.entity;

import com.descenty.work_in_spring.area.Area;
import com.descenty.work_in_spring.user.entity.VacancyResponse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "resume")
@Getter
@Setter
public class Resume {
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
}
