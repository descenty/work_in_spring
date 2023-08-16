package com.descenty.work_in_spring.repository;

import com.descenty.work_in_spring.dto.area.AreaCreate;
import com.descenty.work_in_spring.dto.area.AreaDTO;
import com.descenty.work_in_spring.dto.company.CompanyCreate;
import com.descenty.work_in_spring.entity.Company;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.descenty.work_in_spring.entity.Area;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
