package com.descenty.work_in_spring.vacancy.controller;

import com.descenty.work_in_spring.vacancy.service.VacancyService;

import jakarta.validation.Valid;

import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import com.descenty.work_in_spring.vacancy.dto.VacancyModerationRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
        public List<VacancyDTO> getAllByAreaId(@PathVariable Long areaId) {
                return vacancyService.getAllByAreaId(areaId);
        }

        @GetMapping("/companies/{companyId}/vacancies")
        public List<VacancyDTO> getAllByCompanyId(@PathVariable Long companyId) {
                return vacancyService.getAllByCompanyId(companyId);
        }

        @GetMapping("/companies/{companyId}/areas/{areaId}/vacancies")
        public List<VacancyDTO> getAllByCompanyIdAndAreaId(@PathVariable Long companyId, @PathVariable Long areaId) {
                return vacancyService.getAllByCompanyIdAndAreaId(companyId, areaId);
        }

        @GetMapping("/companies/{companyId}/areas/{areaId}/vacancies/{id}")
        public ResponseEntity<VacancyDTO> getByCompanyIdAndAreaIdAndId(@PathVariable Long companyId,
                        @PathVariable Long areaId, @PathVariable UUID id) {
                return vacancyService.getByCompanyIdAndAreaIdAndId(companyId, areaId, id).map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @PostMapping("/companies/{companyId}/areas/{areaId}/vacancies")
        @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<?> create(@PathVariable Long companyId, @PathVariable Long areaId,
                        @Valid @RequestBody VacancyCreate vacancyCreate) {
                Optional<UUID> vacancyId = vacancyService.create(companyId, areaId, vacancyCreate);

                return vacancyId.isPresent()
                                ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                                .buildAndExpand(vacancyId.get()).toUri()).build()
                                : ResponseEntity.notFound().build();
        }

        @PatchMapping("/companies/{companyId}/areas/{areaId}/vacancies/{id}")
        @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<VacancyDTO> partialUpdate(@PathVariable Long companyId, @PathVariable Long areaId,
                        @PathVariable UUID id, @RequestBody VacancyCreate vacancyCreate) {
                return vacancyService.partialUpdate(companyId, areaId, id, vacancyCreate).map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/companies/{companyId}/areas/{areaId}/vacancies/{id}")
        @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<?> delete(@PathVariable Long companyId, @PathVariable Long areaId,
                        @PathVariable UUID id) {
                return vacancyService.delete(companyId, areaId, id) ? ResponseEntity.noContent().build()
                                : ResponseEntity.notFound().build();
        }

        @PostMapping("/companies/{companyId}/areas/{areaId}/vacancies/{id}/moderation")
        @PreAuthorize("hasAuthority('moderator')")
        public ResponseEntity<?> moderate(@PathVariable Long companyId, @PathVariable Long areaId,
                        @PathVariable UUID id, @Valid @RequestBody VacancyModerationRequest moderationRequest) {
                return vacancyService.moderate(companyId, areaId, id, moderationRequest).map(ResponseEntity::ok)
                                .orElse(ResponseEntity.notFound().build());
        }
}