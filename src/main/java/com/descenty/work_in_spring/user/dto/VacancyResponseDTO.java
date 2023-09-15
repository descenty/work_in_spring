package com.descenty.work_in_spring.user.dto;

import java.util.UUID;

import com.descenty.work_in_spring.user.entity.VacancyResponse.Status;

public record VacancyResponseDTO(UUID id, UUID vacancyId, UUID resumeId, String coverLetter, Integer salary,
        Status status) {
}
