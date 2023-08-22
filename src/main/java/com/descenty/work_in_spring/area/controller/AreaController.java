package com.descenty.work_in_spring.area.controller;

import com.descenty.work_in_spring.area.service.AreaService;
import com.descenty.work_in_spring.area.dto.AreaCreate;
import com.descenty.work_in_spring.area.dto.AreaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping("")
    public List<AreaDTO> getAll() {
        return areaService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> getById(@PathVariable Long id) {
        return areaService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public AreaDTO create(@RequestBody AreaCreate areaCreate) {
        return areaService.create(areaCreate).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<AreaDTO> update(@PathVariable Long
                                                  id, @RequestBody AreaCreate areaCreate) {
        return areaService.update(id, areaCreate).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return areaService.delete(id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}