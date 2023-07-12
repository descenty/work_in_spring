package com.descenty.work_in_spring.entity.collected;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "country")
@Getter
@Setter
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    private City capital;
    @ManyToOne(fetch = FetchType.LAZY)
    private Currency currency;

}
