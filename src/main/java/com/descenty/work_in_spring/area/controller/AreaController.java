package com.descenty.work_in_spring.area.controller;

import com.descenty.work_in_spring.area.service.AreaService;
import com.descenty.work_in_spring.area.dto.AreaCreate;
import com.descenty.work_in_spring.area.dto.AreaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/areas")
@RequiredArgsConstructor
public class AreaController {
    private final AreaService areaService;

    @GetMapping("")
    public List<AreaDTO> getAllParents() {
        return areaService.getAllParents();
    }

    @GetMapping("{id}/main")
    public ResponseEntity<List<AreaDTO>> getAllMain(@PathVariable Long id) {
        return areaService.getAllMain(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaDTO> getById(@PathVariable Long id) {
        return areaService.getById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<AreaDTO> create(@RequestBody AreaCreate areaCreate) {
        return areaService.create(areaCreate).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<AreaDTO> update(@PathVariable Long id,
            @RequestBody AreaCreate areaCreate) {
        return areaService.update(id, areaCreate).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        return areaService.delete(id) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}