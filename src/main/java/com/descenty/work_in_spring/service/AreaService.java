package com.descenty.work_in_spring.service;

import com.descenty.work_in_spring.dto.area.AreaCreate;
import com.descenty.work_in_spring.dto.area.AreaDTO;
import com.descenty.work_in_spring.mapper.AreaMapper;
import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.repository.AreaRepository;

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

    public Optional<AreaDTO> create(AreaCreate areaCreate) {
        return Stream.of(areaCreate)
                .filter(a -> a.getParentId() == null ||
                        areaRepository.findById(a.getParentId()).isPresent())
                .map(areaMapper::toEntity)
                .map(areaRepository::save)
                .map(areaMapper::toDTO)
                .findFirst();
    }


    public Optional<AreaDTO> update(Long id, AreaCreate areaCreate) {
        return areaRepository.findById(id).map(area -> areaMapper.update(area, areaCreate)).map(areaRepository::save).map(areaMapper::toDTO);
    }

    public Optional<Long> delete(Long id) throws IllegalArgumentException {
        try {
            areaRepository.deleteById(id);
            return Optional.of(id);
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

}
