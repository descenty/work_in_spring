package com.descenty.work_in_spring.entity.resume;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "employment")
@Getter
@Setter
public class PrevEmployment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String company;
    private String position;
    private String description;
    private Date startDate;
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    private Resume resume;
}
