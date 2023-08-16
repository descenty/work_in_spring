package com.descenty.work_in_spring.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "area")
@Getter
@Setter
public class Area {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "parent_id", insertable = false, updatable = false)
    private Area parent;
    @Column(name = "parent_id", nullable = true)
    private Long parentId;
}
