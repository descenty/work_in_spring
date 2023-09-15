package com.descenty.work_in_spring.user.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.descenty.work_in_spring.user.entity.VacancyResponse;

public interface VacancyResponseRepository extends JpaRepository<VacancyResponse, UUID> {
    List<VacancyResponse> findAllByVacancyId(UUID vacancyId);
}