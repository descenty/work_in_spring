package com.descenty.work_in_spring.vacancy.repository;

import com.descenty.work_in_spring.vacancy.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, UUID> {
    List<Vacancy> findAllByAreaId(Long areaId);

    List<Vacancy> findAllByCompanyId(Long companyId);

    List<Vacancy> findAllByAreaIdAndCompanyId(Long areaId, Long companyId);

    Optional<Vacancy> findByAreaIdAndCompanyIdAndId(Long areaId, Long companyId, UUID id);

    boolean existsByAreaIdAndCompanyId(Long areaId, Long companyId);

    Long deleteByAreaIdAndCompanyIdAndId(Long areaId, Long companyId, UUID id);

}