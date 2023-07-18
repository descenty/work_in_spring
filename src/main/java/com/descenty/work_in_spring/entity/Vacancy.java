package com.descenty.work_in_spring.entity;

import java.sql.Timestamp;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.descenty.work_in_spring.entity.collected.City;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
public class Vacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ?SEQUENCE
    private Long id;
    private String title;
    private String description;
    private Integer minSalary;
    private Integer maxSalary;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
    @OneToMany(mappedBy = "vacancy")
    private List<UserResponse> userResponse;
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
