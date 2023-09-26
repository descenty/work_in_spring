package com.descenty.work_in_spring.vacancy.service;

import com.descenty.work_in_spring.area.repository.AreaRepository;
import com.descenty.work_in_spring.company.repository.CompanyRepository;
import com.descenty.work_in_spring.vacancy.VacancyMapper;
import com.descenty.work_in_spring.vacancy.repository.VacancyRepository;
import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import com.descenty.work_in_spring.vacancy.dto.VacancyModerationRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
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

    public List<VacancyDTO> getAllByCompanyIdAndAreaId(Long companyId, Long areaId) {
        return vacancyRepository.findAllByAreaId(areaId).stream().map(vacancyMapper::toDTO)
                .toList();
    }

    public Optional<VacancyDTO> getByCompanyIdAndAreaIdAndId(Long companyId, Long areaId, UUID id) {
        return vacancyRepository.findByAreaIdAndId(areaId, id).map(vacancyMapper::toDTO);
    }

    @Transactional
    public Optional<UUID> create(Long companyId, Long areaId, VacancyCreate vacancyCreate)
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
    public Optional<VacancyDTO> partialUpdate(Long companyId, Long areaId, UUID id, VacancyCreate vacancyCreate)
            throws ResponseStatusException {
        if (!companyRepository.existsById(companyId) || !areaRepository.existsById(areaId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Company or area not found");

        return vacancyRepository.findByAreaIdAndId(areaId, id)
                .map(vacancy -> vacancyMapper.update(vacancy, vacancyCreate)).map(vacancyRepository::save)
                .map(vacancyMapper::toDTO);
    }

    @Transactional
    public boolean delete(Long companyId, Long areaId, UUID id) {
        return vacancyRepository.deleteByAreaIdAndId(areaId, id) > 0;
    }

    public Optional<VacancyDTO> moderate(Long companyId, Long areaId, UUID id,
            VacancyModerationRequest moderationRequest) throws ResponseStatusException {
        return vacancyRepository.findByAreaIdAndId(areaId, id)
                .map(vacancy -> vacancyMapper.update(vacancy, moderationRequest)).map(vacancyRepository::save)
                .map(vacancyMapper::toDTO);
    }

}
