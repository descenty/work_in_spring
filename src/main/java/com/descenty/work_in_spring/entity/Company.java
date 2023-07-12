package com.descenty.work_in_spring.entity;

import com.descenty.work_in_spring.entity.collected.City;
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
    private String name;
    private String logo;
    private String website;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    private City city;
}
