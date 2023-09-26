package com.descenty.work_in_spring.vacancy.dto;

import java.sql.Timestamp;
import java.util.UUID;

public record VacancyDTO(UUID id, String title, String description, Integer minSalary, Integer maxSalary, Long areaId,
        Long companyId, Boolean isPublished, Boolean isInArchive, Timestamp publishedAt, Timestamp archivedAt,
        Timestamp createdAt, String moderationStatus) {
}
