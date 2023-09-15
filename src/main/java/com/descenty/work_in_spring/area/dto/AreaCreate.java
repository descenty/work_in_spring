package com.descenty.work_in_spring.area.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class AreaCreate {
    private Long id;
    @NotEmpty
    private String name;
    private Long parentId;
}
