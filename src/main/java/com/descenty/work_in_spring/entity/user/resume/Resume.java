package com.descenty.work_in_spring.entity.user.resume;

import com.descenty.work_in_spring.entity.Area;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "resume")
@Getter
@Setter
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String text;
    private Integer salary;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "area_id", insertable = false, updatable = false)
    private Area area;
    @Column(name = "area_id", nullable = true)
    private Long areaId;
}
