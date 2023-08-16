package com.descenty.work_in_spring.dto.area;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AreaDTO {
    private Long id;
    private String name;
    private Long parentId;
}
