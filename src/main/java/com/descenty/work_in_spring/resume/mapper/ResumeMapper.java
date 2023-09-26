package com.descenty.work_in_spring.resume.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.descenty.work_in_spring.resume.dto.ResumeCreate;
import com.descenty.work_in_spring.resume.dto.ResumeDTO;
import com.descenty.work_in_spring.resume.dto.ResumeModerationRequest;
import com.descenty.work_in_spring.resume.entity.Resume;

@Mapper(componentModel = "spring")
public interface ResumeMapper {
    ResumeDTO toDTO(Resume resume);

    Resume toEntity(ResumeCreate resumeCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Resume update(@MappingTarget Resume resume, ResumeCreate resumeCreate);

    @BeanMapping(nullValuePropertyMappingStrategy = org.mapstruct.NullValuePropertyMappingStrategy.IGNORE)
    Resume update(@MappingTarget Resume resume, ResumeModerationRequest resumeModerationRequest);

}
