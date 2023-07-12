package com.descenty.work_in_spring.entity.resume;

import com.descenty.work_in_spring.entity.collected.City;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "vacancy")
@Getter
@Setter
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // SEQUENCE?
    private Long id;
    private String title;
    private String text;
    private Integer salary;
    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "city_id")
    private City city;
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PrevEmployment> prevEmployment;
}
