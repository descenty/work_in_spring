package com.descenty.work_in_spring.vacancy;

import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface VacancyMapper {
    VacancyDTO toDTO(Vacancy company);

    Vacancy toEntity(VacancyCreate companyCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Vacancy update(@MappingTarget Vacancy company, VacancyCreate companyCreate);

}
