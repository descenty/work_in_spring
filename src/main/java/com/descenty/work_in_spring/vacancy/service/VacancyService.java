package com.descenty.work_in_spring.vacancy.service;

import com.descenty.work_in_spring.area.repository.AreaRepository;
import com.descenty.work_in_spring.company.repository.CompanyRepository;
import com.descenty.work_in_spring.vacancy.VacancyMapper;
import com.descenty.work_in_spring.vacancy.repository.VacancyRepository;
import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VacancyService {
    private final VacancyRepository vacancyRepository;
    private final VacancyMapper vacancyMapper;
    private final CompanyRepository companyRepository;
    private final AreaRepository areaRepository;

    public List<VacancyDTO> getAllByAreaId(Long areaId) {
        return vacancyRepository.findAllByAreaId(areaId).stream().map(vacancyMapper::toDTO).toList();
    }

    public List<VacancyDTO> getAllByCompanyId(Long companyId) {
        return vacancyRepository.findAllByCompanyId(companyId).stream().map(vacancyMapper::toDTO).toList();
    }

    public List<VacancyDTO> getAllByAreaIdAndCompanyId(Long areaId, Long companyId) {
        return vacancyRepository.findAllByAreaIdAndCompanyId(areaId, companyId).stream().map(vacancyMapper::toDTO)
                .toList();
    }

    public Optional<VacancyDTO> getById(Long areaId, Long companyId, UUID id) {
        return vacancyRepository.findByAreaIdAndCompanyIdAndId(areaId, companyId, id).map(vacancyMapper::toDTO);
    }

    @Transactional
    public Optional<UUID> create(Long companyId, Long areaId, VacancyCreate vacancyCreate, UUID employerId)
            throws ResponseStatusException {
        if (!companyRepository.existsById(companyId) || !areaRepository.existsById(areaId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company or area not found");

        return Optional.of(vacancyCreate).map(vacancy -> {
            vacancy.setAreaId(areaId);
            vacancy.setCompanyId(companyId);
            return vacancyMapper.toEntity(vacancy);
        }).map(vacancyRepository::save).map(vacancy -> vacancy.getId());
    }

    @Transactional
    public Optional<VacancyDTO> update(Long companyId, Long areaId, UUID id, VacancyCreate vacancyCreate,
            UUID employerId) throws ResponseStatusException {
        if (!companyRepository.existsById(companyId) || !areaRepository.existsById(areaId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company or area not found");
        if (!companyRepository.existsByIdAndEmployersIdsContaining(companyId, employerId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an employer of this company");

        return vacancyRepository.findByAreaIdAndCompanyIdAndId(areaId, companyId, id)
                .map(vacancy -> vacancyMapper.update(vacancy, vacancyCreate)).map(vacancyRepository::save)
                .map(vacancyMapper::toDTO);
    }

    @Transactional
    public boolean delete(Long areaId, Long companyId, UUID id, UUID employerId) {
        if (!companyRepository.existsByIdAndEmployersIdsContaining(companyId, employerId))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not an employer of this company");

        return vacancyRepository.deleteByCompanyIdAndId(areaId, companyId, id) > 0;
    }

}
