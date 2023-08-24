package com.descenty.work_in_spring.area.repository;

import com.descenty.work_in_spring.area.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AreaRepository extends JpaRepository<Area, Long> {
    List<Area> findAllByParentIdIsNull();
    List<Area> findAllByParentId(Long parentId);
    Optional<Area> findByName(String name);
}
