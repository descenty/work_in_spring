package com.descenty.work_in_spring.area;

import com.descenty.work_in_spring.area.dto.AreaCreate;
import com.descenty.work_in_spring.area.dto.AreaDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    AreaDTO toDTO(Area area);

    Area toEntity(AreaCreate areaCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Area update(@MappingTarget Area area, AreaCreate areaCreate);

}
