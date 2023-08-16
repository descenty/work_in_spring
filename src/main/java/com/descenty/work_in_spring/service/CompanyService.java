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

    public List<CompanyDTO> getAll() {
        return companyRepository.findAll().stream().map(companyMapper::toDTO).toList();
    }

    public Optional<CompanyDTO> getById(Long id) {
        return companyRepository.findById(id).map(companyMapper::toDTO);
    }

    public Optional<CompanyDTO> create(CompanyCreate companyCreate) {
        return Optional.of(companyCreate)
                .map(companyMapper::toEntity)
                .map(companyRepository::save)
                .map(companyMapper::toDTO);
    }

    public Optional<CompanyDTO> update(Long id, CompanyCreate companyCreate) {
        return companyRepository.findById(id).map(company -> companyMapper.update(company, companyCreate)).map(companyRepository::save).map(companyMapper::toDTO);
    }

    public Optional<Long> delete(Long id) {
        try {
            companyRepository.deleteById(id);
            return Optional.of(id);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
