package com.descenty.work_in_spring.dto.vacancy;

import lombok.Data;

@Data
public class VacancyDTO {
    private Long id;
    private String title;
    private String description;
    private Integer minSalary;
    private Integer maxSalary;
    private Long cityId;
}
