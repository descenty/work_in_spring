package com.descenty.work_in_spring.vacancy.service;

import com.descenty.work_in_spring.area.repository.AreaRepository;
import com.descenty.work_in_spring.company.repository.CompanyRepository;
import com.descenty.work_in_spring.vacancy.VacancyMapper;
import com.descenty.work_in_spring.vacancy.repository.VacancyRepository;
import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    public List<VacancyDTO> getAllInArea(Long areaId) {
        return vacancyRepository.findAllByAreaId(areaId).stream().map(vacancyMapper::toDTO).toList();
    }

    public List<VacancyDTO> getAllInCompany(Long companyId) {
        return vacancyRepository.findAllByCompanyId(companyId).stream().map(vacancyMapper::toDTO).toList();
    }

    public List<VacancyDTO> getAllInAreaAndCompany(Long areaId, Long companyId) {
        return vacancyRepository.findAllByAreaIdAndCompanyId(areaId, companyId).stream().map(vacancyMapper::toDTO)
                .toList();
    }

    public Optional<VacancyDTO> getById(Long areaId, Long companyId, UUID id) {
        return vacancyRepository.findByAreaIdAndCompanyIdAndId(areaId, companyId, id).map(vacancyMapper::toDTO);
    }

    @Transactional
    public Optional<UUID> create(Long companyId, Long areaId, VacancyCreate vacancyCreate) {
        if (!companyRepository.existsById(companyId) || !areaRepository.existsById(areaId))
            return Optional.empty();
        return Optional.of(vacancyCreate).map(vacancy -> {
            vacancy.setAreaId(areaId);
            vacancy.setCompanyId(companyId);
            return vacancyMapper.toEntity(vacancy);
        }).map(vacancyRepository::save).map(vacancy -> vacancy.getId());
    }

    @Transactional
    public Optional<VacancyDTO> update(Long areaId, Long companyId, UUID id, VacancyCreate vacancyCreate) {
        return vacancyRepository.findByAreaIdAndCompanyIdAndId(areaId, companyId, id)
                .map(vacancy -> vacancyMapper.update(vacancy, vacancyCreate)).map(vacancyRepository::save)
                .map(vacancyMapper::toDTO);
    }

    @Transactional
    public boolean delete(Long areaId, Long companyId, UUID id) {
        return vacancyRepository.deleteByAreaIdAndCompanyIdAndId(areaId, companyId, id) > 0;
    }

}
