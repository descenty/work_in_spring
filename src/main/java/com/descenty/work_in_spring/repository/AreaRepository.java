package com.descenty.work_in_spring.repository;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import com.descenty.work_in_spring.entity.Area;

@Repository
public interface AreaRepository extends R2dbcRepository<Area, Integer> {
}
