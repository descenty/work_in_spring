package com.descenty.work_in_spring.area.service;

import com.descenty.work_in_spring.area.AreaMapper;
import com.descenty.work_in_spring.area.constant.MainAreas;
import com.descenty.work_in_spring.area.dto.AreaCreate;
import com.descenty.work_in_spring.area.dto.AreaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.area.repository.AreaRepository;

import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;
    private final AreaMapper areaMapper;

    public List<AreaDTO> getAllParents() {
        return areaRepository.findAllByParentIdIsNull().stream().map(areaMapper::toDTO).toList();
    }

    public Optional<List<AreaDTO>> getAllMain(Long id) {
        Optional<AreaDTO> area = areaRepository.findById(id).map(areaMapper::toDTO);
        if (area.isEmpty())
            return Optional.empty();
        try {
            ObjectMapper mapper = new ObjectMapper();
            InputStream inputStream = new ClassPathResource("static/area/main_areas.json").getInputStream();
            MainAreas mainAreas = mapper.readValue(inputStream, MainAreas.class);
            String[] childAreas = mainAreas.areas.get(area.get().getName());
            if (childAreas == null)
                return Optional.empty();
            return Optional.of(Arrays.stream(childAreas).map(areaRepository::findByName).filter(Optional::isPresent)
                    .map(Optional::get).map(areaMapper::toDTO).toList());
        } catch (IOException e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public Optional<AreaDTO> getById(Long id) {
        return areaRepository.findById(id).map(areaMapper::toDTO);
    }

    @Transactional
    public Optional<Long> create(AreaCreate areaCreate) {
        if (areaCreate.getParentId() != null && !areaRepository.existsById(areaCreate.getParentId()))
            return Optional.empty();
        return Optional.of(areaMapper.toEntity(areaCreate)).map(areaRepository::save).map(area -> area.getId());
    }

    @Transactional
    public Optional<AreaDTO> partialUpdate(Long id, AreaCreate areaCreate) {
        return areaRepository.findById(id).map(area -> areaMapper.update(area, areaCreate)).map(areaRepository::save)
                .map(areaMapper::toDTO);
    }

    @Transactional
    public AreaDTO update(Long id, AreaCreate areaCreate) {
        return Optional.of(areaCreate).map(area -> {
            area.setId(id);
            return area;
        }).map(areaMapper::toEntity).map(areaRepository::save).map(areaMapper::toDTO).get();
    }

    @Transactional
    public boolean delete(Long id) {
        return areaRepository.findById(id).map(area -> {
            areaRepository.delete(area);
            return true;
        }).orElse(false);
    }

}
