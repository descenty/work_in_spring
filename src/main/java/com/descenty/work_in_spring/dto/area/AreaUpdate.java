package com.descenty.work_in_spring.dto.area;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AreaUpdate {
    private String name;
    private Long parentId;
}
