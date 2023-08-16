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
@RequestMapping("/companies")
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("")
    public List<CompanyDTO> getAll() {
        return companyService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getById(@PathVariable Long id) {
        return companyService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public CompanyDTO create(@RequestBody CompanyCreate companyCreate) {
        return companyService.create(companyCreate).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CompanyDTO> update(@PathVariable Long
                                                     id, @RequestBody CompanyCreate companyCreate) {
        return companyService.update(id, companyCreate).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return companyService.delete(id).map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    // TODO use kotlin microservice instead
//    @PostMapping("/{id}/upload-logo")
//    public ResponseEntity<?> uploadLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        return companyService.uploadLogo(id, file).map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

}
