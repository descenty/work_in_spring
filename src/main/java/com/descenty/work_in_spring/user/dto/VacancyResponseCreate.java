package com.descenty.work_in_spring.user.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VacancyResponseCreate {
    private String coverLetter;
    @NotNull
    private UUID resumeId;
    private UUID vacancyId;
}
