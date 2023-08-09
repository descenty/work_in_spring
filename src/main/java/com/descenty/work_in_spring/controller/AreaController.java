package com.descenty.work_in_spring.controller;

import com.descenty.work_in_spring.entity.Area;
import com.descenty.work_in_spring.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping("")
    public ResponseEntity<Flux<Area>> getAll() {
        return ResponseEntity.ok(areaService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mono<Area>> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(areaService.getById(id));
    }

    @PostMapping("")
    public ResponseEntity<Mono<Integer>> create(@RequestBody Area area) {
        return ResponseEntity.ok(areaService.create(area));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Mono<Area>> update(@PathVariable Integer id,
            @RequestBody Area area) {
        return ResponseEntity.ok(areaService.update(id, area));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Integer id) {
        return ResponseEntity.ok(areaService.delete(id));
    }

}
