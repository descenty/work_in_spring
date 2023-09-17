package com.descenty.work_in_spring.resume.dto;

import java.util.UUID;

public record ResumeDTO(UUID id, String position, String description, Integer salary, Long areaId, UUID userId) {
}