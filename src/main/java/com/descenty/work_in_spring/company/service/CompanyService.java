package com.descenty.work_in_spring.company.service;

import com.descenty.work_in_spring.company.Company;
import com.descenty.work_in_spring.company.dto.CompanyCreate;
import com.descenty.work_in_spring.company.dto.CompanyDTO;
import com.descenty.work_in_spring.area.repository.AreaRepository;
import com.descenty.work_in_spring.company.CompanyMapper;
import com.descenty.work_in_spring.company.repository.CompanyRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;
    private final AreaRepository areaRepository;

    public List<CompanyDTO> getAllInArea(Long areaId) {
        return companyRepository.findAllByAreaId(areaId).stream().map(companyMapper::toDTO).toList();
    }

    public Optional<CompanyDTO> getById(Long id) {
        return companyRepository.findById(id).map(companyMapper::toDTO);
    }

    @Transactional
    public Optional<Long> create(Long areaId, CompanyCreate companyCreate, UserDetails userDetails) {
        if (!areaRepository.existsById(areaId))
            return Optional.empty();
        return Optional.of(companyCreate).map(company -> {
            company.setAreaId(areaId);
            company.setEmployersEmails(Collections.singletonList(userDetails.getUsername()));
            return companyMapper.toEntity(company);
        }).map(companyRepository::save).map(Company::getId);
    }

    @Transactional
    public Optional<CompanyDTO> update(Long id, CompanyCreate companyCreate, UserDetails userDetails) {
        if (!companyRepository.existsByIdAndEmployersEmailsContaining(id, userDetails.getUsername()))
            return Optional.empty();
        return companyRepository.findById(id).map(company -> companyMapper.update(company, companyCreate))
                .map(companyRepository::save).map(companyMapper::toDTO);
    }

    @Transactional
    public boolean delete(Long id, UserDetails userDetails) {
        if (!companyRepository.existsByIdAndEmployersEmailsContaining(id, userDetails.getUsername()))
            return false;
        return companyRepository.findById(id).map(area -> {
            companyRepository.delete(area);
            return true;
        }).orElse(false);
    }

}
