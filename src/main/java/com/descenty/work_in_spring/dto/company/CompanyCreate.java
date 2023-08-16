package com.descenty.work_in_spring.dto.company;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyCreate {
    private Long id;
    private String name;
    private String logo;
    private String website;
    private String description;
    private Long areaId;
}
