package com.descenty.work_in_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.descenty.work_in_spring.entity.collected.City;

public interface CityRepository extends JpaRepository<City, Long> {
}
