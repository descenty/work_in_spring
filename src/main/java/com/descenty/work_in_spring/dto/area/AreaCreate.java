package com.descenty.work_in_spring.dto.area;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AreaCreate {
    private Long id;
    private String name;
    private Long parentId;
}
