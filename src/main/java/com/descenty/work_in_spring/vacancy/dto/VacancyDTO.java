package com.descenty.work_in_spring.vacancy.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
public class VacancyDTO {
    private UUID id;
    private String title;
    private String description;
    private Integer minSalary;
    private Integer maxSalary;
    private Long areaId;
    private Long companyId;
    private Boolean isPublished;
    private Boolean isInArchive;
    private Timestamp publishedAt;
    private Timestamp archivedAt;
}
