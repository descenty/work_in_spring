package com.descenty.work_in_spring.company;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.descenty.work_in_spring.area.Area;
import com.descenty.work_in_spring.vacancy.Vacancy;
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

    @Column(nullable = false)
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

    @Column(name = "creator_id", nullable = false)
    private UUID creatorId;

    @ElementCollection
    @CollectionTable(name = "company_employers", joinColumns = @JoinColumn(name = "company_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
    @JoinColumn(name = "employer_id")
    private List<UUID> employersIds;

    @OneToMany(mappedBy = "company", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Vacancy> vacancies;
}
