package com.descenty.work_in_spring.user.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.user.dto.VacancyResponseCreate;
import com.descenty.work_in_spring.user.dto.VacancyResponseDTO;
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

    public List<VacancyResponseDTO> getAllByVacancyId(UUID vacancyId) {
        return vacancyResponseRepository.findAllByVacancyId(vacancyId).stream().map(vacancyResponseMapper::toDTO)
                .toList();
    }

    public Optional<VacancyResponseDTO> getById(UUID id) {
        return vacancyResponseRepository.findById(id).map(vacancyResponseMapper::toDTO);
    }

    public Optional<UUID> create(UUID vacancyId, VacancyResponseCreate vacancyResponseCreate) {
        if (!vacancyRepository.existsById(vacancyId))
            return Optional.empty();
        return Optional
                .of(vacancyResponseRepository.save(vacancyResponseMapper.toEntity(vacancyResponseCreate)).getId());
    }

    public boolean setStatus(UUID id, Status status) {
        return vacancyResponseRepository.findById(id).map(vacancyResponse -> {
            vacancyResponse.setStatus(status);
            vacancyResponseRepository.save(vacancyResponse);
            return true;
        }).orElse(false);
    }

    public boolean delete(UUID id) {
        return vacancyResponseRepository.findById(id).map(vacancyResponse -> {
            vacancyResponseRepository.delete(vacancyResponse);
            return true;
        }).orElse(false);
    }

}
