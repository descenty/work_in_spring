package com.descenty.work_in_spring.entity;

import com.descenty.work_in_spring.entity.collected.City;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
}
