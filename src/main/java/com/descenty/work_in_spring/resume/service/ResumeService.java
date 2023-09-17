package com.descenty.work_in_spring.resume.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.resume.dto.ResumeCreate;
import com.descenty.work_in_spring.resume.dto.ResumeDTO;
import com.descenty.work_in_spring.resume.entity.Resume;
import com.descenty.work_in_spring.resume.mapper.ResumeMapper;
import com.descenty.work_in_spring.resume.repository.ResumeRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeService {
    private final ResumeRepository resumeRepository;
    private final ResumeMapper resumeMapper;

    public List<ResumeDTO> getAllByUserId(UUID userId) {
        return resumeRepository.findAllByUserId(userId).stream().map(resumeMapper::toDTO).toList();
    }

    public Optional<ResumeDTO> getByUserIdAndId(UUID userId, UUID id) {
        return resumeRepository.findByUserIdAndId(userId, id).map(resumeMapper::toDTO);
    }

    @Transactional
    public Optional<UUID> create(UUID userId, ResumeCreate resumeCreate) {
        return Optional.of(resumeCreate).map(resume -> {
            resume.setUserId(userId);
            return resumeMapper.toEntity(resume);
        }).map(resumeRepository::save).map(Resume::getId);
    }

    public Optional<ResumeDTO> update(UUID userId, UUID id, ResumeCreate resumeCreate) {
        return resumeRepository.findByUserIdAndId(userId, id).map(resume -> resumeMapper.update(resume, resumeCreate))
                .map(resumeRepository::save).map(resumeMapper::toDTO);
    }

    public boolean deleteByUserId(UUID userId) {
        return resumeRepository.deleteByUserId(userId) > 0;
    }

    public boolean deleteByUserIdAndId(UUID userId, UUID id) {
        return resumeRepository.deleteByUserIdAndId(userId, id) > 0;
    }
}
