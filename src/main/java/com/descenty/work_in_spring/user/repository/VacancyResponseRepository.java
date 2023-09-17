package com.descenty.work_in_spring.user.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.descenty.work_in_spring.user.entity.VacancyResponse;

public interface VacancyResponseRepository extends JpaRepository<VacancyResponse, UUID> {
    List<VacancyResponse> findAllByVacancyId(UUID vacancyId);

    List<VacancyResponse> findAllByResume_UserId(UUID userId);

    Optional<VacancyResponse> findByResume_UserIdAndId(UUID userId, UUID id);

    Optional<VacancyResponse> findByVacancyIdAndId(UUID vacancyId, UUID id);

    Optional<VacancyResponse> findByVacancyIdAndResume_UserIdAndId(UUID vacancyId, UUID userId, UUID id);

    Optional<VacancyResponse> findByVacancyIdAndResume_UserId(UUID vacancyId, UUID userId);
}