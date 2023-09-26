package com.descenty.work_in_spring.vacancy;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

import com.descenty.work_in_spring.area.Area;
import com.descenty.work_in_spring.company.Company;
import com.descenty.work_in_spring.user.entity.VacancyResponse;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
public class Vacancy {
    public enum ModerationStatus {
        PENDING, ACCEPTED, REJECTED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = true)
    private Integer minSalary;

    @Column(nullable = true)
    private Integer maxSalary;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;

    @Column(name = "area_id", nullable = true)
    private Long areaId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", insertable = false, updatable = false)
    private Company company;

    @Column(name = "company_id", nullable = true)
    private Long companyId;

    @OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<VacancyResponse> responses;

    @Column(name = "moderation_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ModerationStatus moderationStatus = ModerationStatus.PENDING;

    @Column(nullable = false)
    private Boolean isPublished = false;

    @Column(nullable = false)
    private Boolean isInArchive = false;

    private Timestamp publishedAt;

    private Timestamp archivedAt;

    @Column(nullable = false)
    private Timestamp createdAt = new Timestamp(System.currentTimeMillis());

    public void publish() {
        this.isPublished = true;
        this.publishedAt = new Timestamp(System.currentTimeMillis());
    }

    public void archive() {
        this.isInArchive = true;
        this.archivedAt = new Timestamp(System.currentTimeMillis());
    }
}
