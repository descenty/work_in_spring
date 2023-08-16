package com.descenty.work_in_spring.mapper;

import com.descenty.work_in_spring.dto.company.CompanyCreate;
import com.descenty.work_in_spring.dto.company.CompanyDTO;
import com.descenty.work_in_spring.entity.Company;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CompanyMapper {
    CompanyDTO toDTO(Company company);

    Company toEntity(CompanyCreate companyCreate);

    Company update(@MappingTarget Company company, CompanyCreate companyCreate);

}
