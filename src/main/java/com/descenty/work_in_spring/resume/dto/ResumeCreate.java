package com.descenty.work_in_spring.resume.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeCreate {
    @NotEmpty
    String position;
    @NotEmpty
    String description;
    Integer salary;
    UUID userId;
}