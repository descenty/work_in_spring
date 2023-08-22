package com.descenty.work_in_spring.company.repository;

import com.descenty.work_in_spring.company.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<Company> findAllByAreaId(Long areaId);
    Optional<Company> findByAreaIdAndId(Long areaId, Long id);

    Long deleteByAreaIdAndId(Long areaId, Long id);
}
