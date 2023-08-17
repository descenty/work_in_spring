package com.descenty.work_in_spring.entity.user.resume;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "employment")
@Getter
@Setter
public class Employment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String company;
    private String position;
    private String description;
    private Date startDate;
    private Date endDate;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "resume_id", insertable = false, updatable = false)
    private Resume resume;
    @Column(name = "resume_id", nullable = false)
    private UUID resumeId;
}
