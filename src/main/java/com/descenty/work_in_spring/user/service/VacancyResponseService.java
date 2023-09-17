package com.descenty.work_in_spring.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.descenty.work_in_spring.company.repository.CompanyRepository;
import com.descenty.work_in_spring.resume.repository.ResumeRepository;
import com.descenty.work_in_spring.user.dto.VacancyResponseCreate;
import com.descenty.work_in_spring.user.dto.VacancyResponseDTO;
import com.descenty.work_in_spring.user.entity.VacancyResponse;
import com.descenty.work_in_spring.user.entity.VacancyResponse.Status;
import com.descenty.work_in_spring.user.mapper.VacancyResponseMapper;
import com.descenty.work_in_spring.user.repository.VacancyResponseRepository;
import com.descenty.work_in_spring.vacancy.repository.VacancyRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacancyResponseService {
    private final VacancyResponseRepository vacancyResponseRepository;
    private final VacancyResponseMapper vacancyResponseMapper;
    private final VacancyRepository vacancyRepository;
    private final CompanyRepository companyRepository;
    private final ResumeRepository resumeRepository;

    public List<VacancyResponseDTO> getAllByCompanyIdAndVacancyId(Long companyId, UUID vacancyId, UUID employerId)
            throws ResponseStatusException {
        return vacancyResponseRepository.findAllByVacancyId(vacancyId).stream().map(vacancyResponseMapper::toDTO)
                .toList();
    }

    public Optional<VacancyResponseDTO> getByCompanyIdAndVacancyIdAndId(Long companyId, UUID vacancyId, UUID id,
            UUID employerId) throws ResponseStatusException {
        return vacancyResponseRepository.findByVacancyIdAndId(vacancyId, id).map(vacancyResponseMapper::toDTO);
    }

    public Optional<VacancyResponseDTO> getByVacancyIdAndUserIdAndId(UUID vacancyId, UUID userId, UUID id) {
        return vacancyResponseRepository.findByVacancyIdAndResume_UserIdAndId(vacancyId, userId, id)
                .map(vacancyResponseMapper::toDTO);
    }

    public List<VacancyResponseDTO> getAllByUserId(UUID userId) {
        return vacancyResponseRepository.findAllByResume_UserId(userId).stream().map(vacancyResponseMapper::toDTO)
                .toList();
    }

    public Optional<VacancyResponseDTO> getByUserIdAndId(UUID userId, UUID id) {
        return vacancyResponseRepository.findByResume_UserIdAndId(userId, id).map(vacancyResponseMapper::toDTO);
    }

    public Optional<UUID> create(Long companyId, UUID vacancyId, UUID userId,
            VacancyResponseCreate vacancyResponseCreate) {
        if (!vacancyRepository.existsByCompanyIdAndId(companyId, vacancyId)
                || !resumeRepository.existsByUserIdAndId(userId, vacancyResponseCreate.getResumeId()))
            return Optional.empty();
        return Optional.of(vacancyResponseCreate).map(vacancyResponse -> {
            vacancyResponse.setVacancyId(vacancyId);
            return vacancyResponseMapper.toEntity(vacancyResponse);
        }).map(vacancyResponseRepository::save).map(VacancyResponse::getId);
    }

    public boolean setStatus(Long companyId, UUID vacancyId, UUID id, Status status) {
        if (!vacancyRepository.existsByCompanyIdAndId(companyId, vacancyId))
            return false;
        return vacancyResponseRepository.findByVacancyIdAndId(vacancyId, id).map(vacancyResponse -> {
            vacancyResponse.setStatus(status);
            vacancyResponseRepository.save(vacancyResponse);
            return true;
        }).orElse(false);
    }

    public boolean deleteByVacancyIdAndId(UUID vacancyId, UUID id) {
        return vacancyResponseRepository.findByVacancyIdAndId(vacancyId, id).map(vacancyResponse -> {
            vacancyResponseRepository.delete(vacancyResponse);
            return true;
        }).orElse(false);
    }

}
