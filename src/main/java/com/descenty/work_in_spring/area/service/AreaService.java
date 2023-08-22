package com.descenty.work_in_spring.area.service;

import com.descenty.work_in_spring.area.AreaMapper;
import com.descenty.work_in_spring.area.dto.AreaCreate;
import com.descenty.work_in_spring.area.dto.AreaDTO;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.area.repository.AreaRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    public List<AreaDTO> getAll() {
        return areaRepository.findAll().stream().map(areaMapper::toDTO).toList();
    }

    public Optional<AreaDTO> getById(Long id) {
        return areaRepository.findById(id).map(areaMapper::toDTO);
    }

    @Transactional
    public Optional<AreaDTO> create(AreaCreate areaCreate) {
        return Stream.of(areaCreate)
                .filter(a -> a.getParentId() == null ||
                        areaRepository.findById(a.getParentId()).isPresent())
                .map(areaMapper::toEntity)
                .map(areaRepository::save)
                .map(areaMapper::toDTO)
                .findFirst();
    }


    @Transactional
    public Optional<AreaDTO> update(Long id, AreaCreate areaCreate) {
        return areaRepository.findById(id).map(area -> areaMapper.update(area, areaCreate)).map(areaRepository::save).map(areaMapper::toDTO);
    }

    @Transactional
    public boolean delete(Long id) {
        return areaRepository.findById(id).map(area -> {
            areaRepository.delete(area);
            return true;
        }).orElse(false);
    }

}
