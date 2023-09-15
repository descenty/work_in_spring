package com.descenty.work_in_spring.vacancy.controller;

import com.descenty.work_in_spring.vacancy.service.VacancyService;

import jakarta.validation.Valid;

import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class VacancyController {
    private final VacancyService vacancyService;

    @GetMapping("/areas/{areaId}/vacancies")
    public List<VacancyDTO> getAllInArea(@PathVariable Long areaId) {
        return vacancyService.getAllInArea(areaId);
    }

    @GetMapping("/companies/{companyId}/vacancies")
    public List<VacancyDTO> getAllInCompany(@PathVariable Long companyId) {
        return vacancyService.getAllInCompany(companyId);
    }

    @GetMapping("/areas/{areaId}/companies/{companyId}/vacancies")
    public List<VacancyDTO> getAllInAreaAndCompany(@PathVariable Long areaId, @PathVariable Long companyId) {
        return vacancyService.getAllInAreaAndCompany(areaId, companyId);
    }

    @GetMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    public ResponseEntity<VacancyDTO> getById(@PathVariable Long areaId, @PathVariable Long companyId,
            @PathVariable UUID id) {
        return vacancyService.getById(areaId, companyId, id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/companies/{companyId}/areas/{areaId}/vacancies")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('employer')")
    public ResponseEntity<?> create(@PathVariable Long companyId, @PathVariable Long areaId,
            @Valid @RequestBody VacancyCreate vacancyCreate, @AuthenticationPrincipal UserDetails userDetails) {
        Optional<UUID> vacancyId = vacancyService.create(companyId, areaId, vacancyCreate, userDetails);
        return vacancyId.isPresent()
                ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(vacancyId.get()).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    @PreAuthorize("hasAuthority('admin') or hasRole('employer')")
    public ResponseEntity<VacancyDTO> update(@PathVariable Long areaId, @PathVariable Long companyId,
            @PathVariable UUID id, @Valid @RequestBody VacancyCreate vacancyCreate,
            @AuthenticationPrincipal UserDetails userDetails) {
        return vacancyService.update(areaId, companyId, id, vacancyCreate, userDetails).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    @PreAuthorize("hasAuthority('admin') or hasRole('employer')")
    public ResponseEntity<?> delete(@PathVariable Long areaId, @PathVariable Long companyId, @PathVariable UUID id,
            @AuthenticationPrincipal UserDetails userDetails) {
        return vacancyService.delete(areaId, companyId, id, userDetails) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }
}