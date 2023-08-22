package com.descenty.work_in_spring.company.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyDTO {
    private Long id;
    private String name;
    private String logo;
    private String website;
    private String description;
    private Long areaId;
}
