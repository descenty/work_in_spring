package com.descenty.work_in_spring.service;

import org.springframework.stereotype.Service;

import com.descenty.work_in_spring.entity.Area;
import com.descenty.work_in_spring.repository.AreaRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class AreaService {
    private final AreaRepository areaRepository;

    public Flux<Area> getAll() {
        return areaRepository.findAll();
    }

    public Mono<Area> getById(Integer id) {
        return areaRepository.findById(id);
    }

    public Mono<Integer> create(Area area) {
        return areaRepository.save(area).map(Area::getId);
    }

    public Mono<Area> update(Integer id, Area area) {
        return areaRepository.findById(id).flatMap(a -> {
            a.setName(area.getName());
            return areaRepository.save(a);
        });
    }

    public Mono<Void> delete(Integer id) {
        return areaRepository.deleteById(id);
    }

}
