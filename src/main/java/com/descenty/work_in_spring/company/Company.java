package com.descenty.work_in_spring.company;

import java.util.List;
import java.util.UUID;

import com.descenty.work_in_spring.area.Area;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "company")
@Getter
@Setter
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = true)
    private String logo;
    @Column(nullable = true)
    private String website;
    @Column(nullable = true)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    @JsonInclude
    private Area area;
    @Column(name = "area_id", nullable = true)
    @JsonIgnore
    private Long areaId;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "company_employer", joinColumns = @JoinColumn(name = "company_id"), inverseJoinColumns = @JoinColumn(name = "employer_email"))
    private List<String> employersEmails;
}
