package com.descenty.work_in_spring.vacancy;

import java.sql.Timestamp;
import java.util.UUID;

import com.descenty.work_in_spring.area.Area;
import com.descenty.work_in_spring.company.Company;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
public class Vacancy {
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

    private Boolean isPublished;
    private Boolean isInArchive;
    private Timestamp publishedAt;
    private Timestamp archivedAt;

    public void publish() {
        this.isPublished = true;
        this.publishedAt = new Timestamp(System.currentTimeMillis());
    }

    public void archive() {
        this.isInArchive = true;
        this.archivedAt = new Timestamp(System.currentTimeMillis());
    }
}
