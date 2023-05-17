package com.descenty.work_in_spring.dto;

import lombok.Data;

@Data
public class VacancyDto {
    private Long id;
    private String title;
    private String description;
    private Integer minSalary;
    private Integer maxSalary;
    private Long cityId;
}
