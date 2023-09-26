package com.descenty.work_in_spring.vacancy;

import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import com.descenty.work_in_spring.vacancy.dto.VacancyModerationRequest;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VacancyMapper {
    VacancyDTO toDTO(Vacancy vacancy);

    Vacancy toEntity(VacancyCreate vacancyCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Vacancy update(@MappingTarget Vacancy vacancy, VacancyCreate vacancyCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Vacancy update(@MappingTarget Vacancy vacancy, VacancyModerationRequest vacancyModerationRequest);
}
