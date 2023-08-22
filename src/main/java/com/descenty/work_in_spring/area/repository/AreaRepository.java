package com.descenty.work_in_spring.area.repository;

import com.descenty.work_in_spring.area.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
}
