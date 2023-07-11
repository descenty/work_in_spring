package com.descenty.work_in_spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.descenty.work_in_spring.entity.Vacancy;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "vacancy")
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {
}
