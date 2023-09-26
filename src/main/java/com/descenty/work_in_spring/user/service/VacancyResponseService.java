package com.descenty.work_in_spring.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.descenty.work_in_spring.resume.repository.ResumeRepository;
import com.descenty.work_in_spring.user.dto.VacancyResponseAnswer;
import com.descenty.work_in_spring.user.dto.VacancyResponseCreate;
import com.descenty.work_in_spring.user.dto.VacancyResponseDTO;
import com.descenty.work_in_spring.user.entity.VacancyResponse;
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
    private final ResumeRepository resumeRepository;

    public List<VacancyResponseDTO> getAllByCompanyIdAndAreaIdAndVacancyId(Long companyId, Long areaId, UUID vacancyId,
            UUID employerId) throws ResponseStatusException {
        return vacancyResponseRepository
                .findAllByVacancy_CompanyIdAndVacancy_AreaIdAndVacancyId(companyId, areaId, vacancyId).stream()
                .map(vacancyResponseMapper::toDTO).toList();
    }

    public Optional<VacancyResponseDTO> getByCompanyIdAndAreaIdAndVacancyIdAndId(Long companyId, Long areaId,
            UUID vacancyId, UUID id, UUID employerId) throws ResponseStatusException {
        return vacancyResponseRepository
                .findByVacancy_CompanyIdAndVacancy_AreaIdAndVacancyIdAndId(companyId, areaId, vacancyId, id)
                .map(vacancyResponseMapper::toDTO);
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

    public List<VacancyResponseDTO> getAllByCompanyIdAndAreaIdAndVacancyIdAndUserId(Long companyId, Long areaId,
            UUID vacancyId, UUID userId) {
        return vacancyResponseRepository
                .findAllByVacancy_CompanyIdAndVacancy_AreaIdAndVacancyIdAndResume_UserId(companyId, areaId, vacancyId,
                        userId)
                .stream().map(vacancyResponseMapper::toDTO).toList();
    }

    public Optional<VacancyResponseDTO> getByCompanyIdAndAreaIdAndVacancyIdAndUserIdAndId(Long companyId, Long areaId,
            UUID vacancyId, UUID userId, UUID id) {
        return vacancyResponseRepository.findByVacancy_CompanyIdAndVacancy_AreaIdAndVacancyIdAndResume_UserIdAndId(
                companyId, areaId, vacancyId, userId, id).map(vacancyResponseMapper::toDTO);
    }

    public Optional<UUID> create(Long companyId, Long areaId, UUID vacancyId, UUID userId,
            VacancyResponseCreate vacancyResponseCreate) {
        if (!vacancyRepository.existsByCompanyIdAndId(companyId, vacancyId)
                || !resumeRepository.existsByUserIdAndId(userId, vacancyResponseCreate.getResumeId()))
            return Optional.empty();
        return Optional.of(vacancyResponseCreate).map(vacancyResponse -> {
            vacancyResponse.setVacancyId(vacancyId);
            return vacancyResponseMapper.toEntity(vacancyResponse);
        }).map(vacancyResponseRepository::save).map(VacancyResponse::getId);
    }

    public Optional<VacancyResponseDTO> partialUpdateByCompanyIdAndAreaIdAndVacancyIdAndUserIdAndId(Long companyId,
            Long areaId, UUID vacancyId, UUID userId, UUID id, VacancyResponseCreate vacancyResponseCreate) {
        return vacancyResponseRepository
                .findByVacancy_CompanyIdAndVacancy_AreaIdAndVacancyIdAndResume_UserIdAndId(companyId, areaId, vacancyId,
                        userId, id)
                .map(vacancyResponse -> vacancyResponseMapper.update(vacancyResponse, vacancyResponseCreate))
                .map(vacancyResponseRepository::save).map(vacancyResponseMapper::toDTO);
    }

    public boolean deleteByCompanyIdAndAreaIdAndVacancyIdAndId(Long companyId, Long areaId, UUID vacancyId, UUID id) {
        return vacancyResponseRepository
                .findByVacancy_CompanyIdAndVacancy_AreaIdAndVacancyIdAndId(companyId, areaId, vacancyId, id)
                .map(vacancyResponse -> {
                    vacancyResponseRepository.delete(vacancyResponse);
                    return true;
                }).orElse(false);
    }

    public Optional<VacancyResponseDTO> answer(Long companyId, Long areaId, UUID vacancyId, UUID id,
            VacancyResponseAnswer vacancyResponseAnswer) {
        return vacancyResponseRepository
                .findByVacancy_CompanyIdAndVacancy_AreaIdAndVacancyIdAndId(companyId, areaId, vacancyId, id)
                .map(vacancyResponse -> vacancyResponseMapper.update(vacancyResponse, vacancyResponseAnswer))
                .map(vacancyResponseRepository::save).map(vacancyResponseMapper::toDTO);
    }
}
