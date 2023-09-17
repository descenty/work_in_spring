package com.descenty.work_in_spring.resume.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.descenty.work_in_spring.resume.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, UUID> {
    List<Resume> findAllByUserId(UUID userId);

    Optional<Resume> findByUserIdAndId(UUID userId, UUID id);

    boolean existsByUserIdAndId(UUID userId, UUID id);

    Integer deleteByUserIdAndId(UUID userId, UUID id);

    Integer deleteByUserId(UUID userId);
}
