package com.descenty.work_in_spring.dto.company;

import com.descenty.work_in_spring.entity.Area;
import jakarta.persistence.*;
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
