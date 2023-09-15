package com.descenty.work_in_spring.company.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CompanyCreate {
    private Long id;
    @NotEmpty
    private String name;
    private String logo;
    private String website;
    private String description;
    @NotNull
    private Long areaId;
    private List<String> employersEmails;
}
