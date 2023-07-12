package com.descenty.work_in_spring.repository;

import com.descenty.work_in_spring.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "company")
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
