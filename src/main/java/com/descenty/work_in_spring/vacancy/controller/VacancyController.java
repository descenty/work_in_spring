package com.descenty.work_in_spring.vacancy.controller;

import com.descenty.work_in_spring.vacancy.service.VacancyService;

import jakarta.validation.Valid;

import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
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
@RequestMapping("")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping("/areas/{areaId}/vacancies")
    public List<VacancyDTO> getAllByAreaId(@PathVariable Long areaId) {
        return vacancyService.getAllByAreaId(areaId);
    }

    @GetMapping("/companies/{companyId}/vacancies")
    public List<VacancyDTO> getAllByCompanyId(@PathVariable Long companyId) {
        return vacancyService.getAllByCompanyId(companyId);
    }

    @GetMapping("/areas/{areaId}/companies/{companyId}/vacancies")
    public List<VacancyDTO> getAllByAreaIdAndCompanyId(@PathVariable Long areaId, @PathVariable Long companyId) {
        return vacancyService.getAllByAreaIdAndCompanyId(areaId, companyId);
    }

    @GetMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    public ResponseEntity<VacancyDTO> getById(@PathVariable Long areaId, @PathVariable Long companyId,
            @PathVariable UUID id) {
        return vacancyService.getById(areaId, companyId, id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/companies/{companyId}/areas/{areaId}/vacancies")
    @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<?> create(@PathVariable Long companyId, @PathVariable Long areaId,
            @Valid @RequestBody VacancyCreate vacancyCreate, Principal principal) {
        Optional<UUID> vacancyId = vacancyService.create(companyId, areaId, vacancyCreate,
                UUID.fromString(principal.getName()));

        return vacancyId.isPresent()
                ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(vacancyId.get()).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<VacancyDTO> partialUpdate(@PathVariable Long areaId, @PathVariable Long companyId,
            @PathVariable UUID id, @Valid @RequestBody VacancyCreate vacancyCreate, Principal principal) {
        return vacancyService.update(areaId, companyId, id, vacancyCreate, UUID.fromString(principal.getName()))
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<?> delete(@PathVariable Long areaId, @PathVariable Long companyId, @PathVariable UUID id,
            Principal principal) {
        return vacancyService.delete(areaId, companyId, id, UUID.fromString(principal.getName()))
                ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}