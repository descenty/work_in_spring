package com.descenty.work_in_spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.descenty.work_in_spring.entity.City;

public interface CityRepository extends JpaRepository<City, Long> {
    @Query("SELECT c FROM City c WHERE ?1 is null or c.name LIKE %?1%")
    List<City> findAll(String name);
}
