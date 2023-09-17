package com.descenty.work_in_spring.resume.entity;

import com.descenty.work_in_spring.area.Area;
import com.descenty.work_in_spring.user.entity.VacancyResponse;
import com.descenty.work_in_spring.vacancy.Vacancy;

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
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String position;

    @Column(nullable = false)
    private String description;

    private Integer salary;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    @Column(name = "area_id", nullable = true)
    private Long areaId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @OneToMany(mappedBy = "resume", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VacancyResponse> vacancyResponses;
}
