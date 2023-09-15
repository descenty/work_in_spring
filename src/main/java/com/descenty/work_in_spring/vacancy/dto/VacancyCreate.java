package com.descenty.work_in_spring.vacancy.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VacancyCreate {
    @NotEmpty
    private String title;
    @NotEmpty
    private String description;
    @Min(0)
    private Integer minSalary;
    @Min(0)
    private Integer maxSalary;
    private Long areaId;
    private Long companyId;
}
