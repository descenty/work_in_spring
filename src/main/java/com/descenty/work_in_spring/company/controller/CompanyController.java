package com.descenty.work_in_spring.company.controller;

import com.descenty.work_in_spring.company.service.CompanyService;

import jakarta.validation.Valid;

import com.descenty.work_in_spring.company.dto.CompanyCreate;
import com.descenty.work_in_spring.company.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class CompanyController {
    private final CompanyService companyService;

    @GetMapping("/areas/{areaId}/companies")
    public List<CompanyDTO> getAllInArea(@PathVariable Long areaId) {
        return companyService.getAllInArea(areaId);
    }

    @GetMapping("/companies/{id}")
    public ResponseEntity<CompanyDTO> getById(@PathVariable Long id) {
        return companyService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/areas/{areaId}/companies")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> create(@PathVariable Long areaId, @Valid @RequestBody CompanyCreate companyCreate,
            Principal principal) {
        Optional<Long> companyId = companyService.create(areaId, companyCreate, UUID.fromString(principal.getName()));
        return companyId.isPresent()
                ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(companyId.get()).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/companies/{id}")
    @PreAuthorize("@companySecurity.isCreator(#id)")
    public ResponseEntity<CompanyDTO> update(@PathVariable Long id, @Valid @RequestBody CompanyCreate companyCreate,
            Principal principal) {
        return companyService.update(id, companyCreate, UUID.fromString(principal.getName())).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/companies/{id}")
    @PreAuthorize("@companySecurity.isCreator(#id)")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        return companyService.delete(id, UUID.fromString(principal.getName())) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

    // TODO use kotlin microservice instead
    // @PostMapping("/{id}/upload-logo")
    // public ResponseEntity<?> uploadLogo(@PathVariable Long id,
    // @RequestParam("file") MultipartFile file) {
    // return companyService.uploadLogo(id,
    // file).map(ResponseEntity::ok).orElseThrow(() -> new
    // ResponseStatusException(HttpStatus.NOT_FOUND));
    // }
}