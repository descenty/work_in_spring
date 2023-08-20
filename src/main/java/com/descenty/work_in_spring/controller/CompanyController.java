package com.descenty.work_in_spring.controller;

import com.descenty.work_in_spring.dto.company.CompanyCreate;
import com.descenty.work_in_spring.dto.company.CompanyDTO;
import com.descenty.work_in_spring.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/areas/")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("{areaId}/companies")
    public List<CompanyDTO> getAll(@PathVariable Long areaId) {
        return companyService.getAll(areaId);
    }

    @GetMapping("/{areaId}/companies/{id}")
    public ResponseEntity<CompanyDTO> getById(@PathVariable Long areaId, @PathVariable Long id) {
        return companyService.getById(areaId, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{areaId}/companies")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO create(@PathVariable Long areaId, @RequestBody CompanyCreate companyCreate) {
        return companyService.create(areaId, companyCreate).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{areaId}/companies/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable Long areaId, @PathVariable Long
            id, @RequestBody CompanyCreate companyCreate) {
        return companyService.update(areaId, id, companyCreate).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{areaId}/companies/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long areaId, @PathVariable Long id) {
        return companyService.delete(areaId, id) > 0 ? ResponseEntity.ok(id) : ResponseEntity.notFound().build();
    }

    // TODO use kotlin microservice instead
//    @PostMapping("/{id}/upload-logo")
//    public ResponseEntity<?> uploadLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        return companyService.uploadLogo(id, file).map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

}
