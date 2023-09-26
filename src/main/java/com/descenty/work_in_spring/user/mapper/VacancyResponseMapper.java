package com.descenty.work_in_spring.user.mapper;

import com.descenty.work_in_spring.user.dto.VacancyResponseAnswer;
import com.descenty.work_in_spring.user.dto.VacancyResponseCreate;
import com.descenty.work_in_spring.user.dto.VacancyResponseDTO;
import com.descenty.work_in_spring.user.entity.VacancyResponse;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VacancyResponseMapper {
    VacancyResponseDTO toDTO(VacancyResponse vacancyResponse);

    VacancyResponse toEntity(VacancyResponseCreate vacancyResponseCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    VacancyResponse update(@MappingTarget VacancyResponse vacancyResponse, VacancyResponseCreate vacancyResponseCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    VacancyResponse update(@MappingTarget VacancyResponse vacancyResponse, VacancyResponseAnswer vacancyResponseAnswer);
}
