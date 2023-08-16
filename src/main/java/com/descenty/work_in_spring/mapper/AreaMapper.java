package com.descenty.work_in_spring.mapper;

import com.descenty.work_in_spring.dto.area.AreaCreate;
import com.descenty.work_in_spring.dto.area.AreaDTO;
import com.descenty.work_in_spring.entity.Area;
import com.descenty.work_in_spring.repository.AreaRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface AreaMapper {
    AreaDTO toDTO(Area area);

    Area toEntity(AreaCreate areaCreate);

    Area update(@MappingTarget Area area, AreaCreate areaCreate);

}
