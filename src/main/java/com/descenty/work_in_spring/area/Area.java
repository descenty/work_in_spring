package com.descenty.work_in_spring.area;

import java.util.List;

import com.descenty.work_in_spring.company.Company;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Area parent;

    @Column(name = "parent_id", nullable = true)
    private Long parentId;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Area> children;

    @OneToMany(mappedBy = "area", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Company> companies;
}
