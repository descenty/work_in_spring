package com.descenty.work_in_spring.controller;

import com.descenty.work_in_spring.dto.area.AreaCreate;
import com.descenty.work_in_spring.dto.area.AreaDTO;
import com.descenty.work_in_spring.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Stream;

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
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return areaService.delete(id).map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

}
