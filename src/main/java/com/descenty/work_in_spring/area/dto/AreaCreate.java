package com.descenty.work_in_spring.area.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AreaCreate {
    private Long id;
    private String name;
    private Long parentId;
}
