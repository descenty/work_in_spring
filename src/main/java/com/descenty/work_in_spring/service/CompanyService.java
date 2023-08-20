package com.descenty.work_in_spring.service;

import com.descenty.work_in_spring.dto.company.CompanyCreate;
import com.descenty.work_in_spring.dto.company.CompanyDTO;
import com.descenty.work_in_spring.mapper.CompanyMapper;
import com.descenty.work_in_spring.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    public List<CompanyDTO> getAll(Long areaId) {
        return companyRepository.findAllByAreaId(areaId).stream().map(companyMapper::toDTO).toList();
    }

    public Optional<CompanyDTO> getById(Long areaId, Long id) {
        return companyRepository.findByAreaIdAndId(areaId, id).map(companyMapper::toDTO);
    }

    public Optional<CompanyDTO> create(Long areaId, CompanyCreate companyCreate) {
        return Optional.of(companyCreate)
                .map(company -> {
                    company.setAreaId(areaId);
                    return companyMapper.toEntity(company);
                })
                .map(companyRepository::save)
                .map(companyMapper::toDTO);
    }

    public Optional<CompanyDTO> update(Long areaId, Long id, CompanyCreate companyCreate) {
        return companyRepository.findByAreaIdAndId(areaId, id).map(company -> companyMapper.update(company, companyCreate)).map(companyRepository::save).map(companyMapper::toDTO);
    }

    public Long delete(Long areaId, Long id) {
        return companyRepository.deleteByAreaIdAndId(areaId, id);
    }

}
