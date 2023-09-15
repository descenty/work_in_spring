package com.descenty.work_in_spring.user.dto;

import java.util.UUID;

public record VacancyResponseCreate(String coverLetter, Integer salary, UUID resumeId) {
}
