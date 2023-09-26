package com.descenty.work_in_spring.user.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.descenty.work_in_spring.user.dto.VacancyResponseAnswer;
import com.descenty.work_in_spring.user.dto.VacancyResponseCreate;
import com.descenty.work_in_spring.user.dto.VacancyResponseDTO;
import com.descenty.work_in_spring.user.service.VacancyResponseService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class VacancyResponseController {
        private final VacancyResponseService vacancyResponseService;

        @GetMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses")
        @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
        public List<VacancyResponseDTO> getAllByCompanyIdAndVacancyId(@PathVariable Long companyId,
                        @PathVariable Long areaId, @PathVariable UUID vacancyId, Principal principal) {
                return vacancyResponseService.getAllByCompanyIdAndAreaIdAndVacancyId(companyId, areaId, vacancyId,
                                UUID.fromString(principal.getName()));
        }

        @GetMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses/{id}")
        @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<VacancyResponseDTO> getByCompanyIdAndVacancyIdAndId(@PathVariable Long companyId,
                        @PathVariable Long areaId, @PathVariable UUID vacancyId, @PathVariable UUID id,
                        Principal principal) {
                return vacancyResponseService
                                .getByCompanyIdAndAreaIdAndVacancyIdAndId(companyId, areaId, vacancyId, id,
                                                UUID.fromString(principal.getName()))
                                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses/self")
        @PreAuthorize("isAuthenticated() && !@companySecurity.isCompanyEmployer(#companyId)")
        public List<VacancyResponseDTO> getAllByCompanyIdAndVacancyIdAndUserId(@PathVariable Long companyId,
                        @PathVariable Long areaId, @PathVariable UUID vacancyId, Principal principal) {
                return vacancyResponseService.getAllByCompanyIdAndAreaIdAndVacancyId(companyId, areaId, vacancyId,
                                vacancyId);
        }

        @GetMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses/{id}/self")
        @PreAuthorize("isAuthenticated() && !@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<VacancyResponseDTO> getByCompanyIdAndVacancyIdAndUserIdAndId(@PathVariable Long companyId,
                        @PathVariable Long areaId, @PathVariable UUID vacancyId, @PathVariable UUID id,
                        Principal principal) {
                return vacancyResponseService
                                .getByCompanyIdAndAreaIdAndVacancyIdAndUserIdAndId(companyId, areaId, vacancyId,
                                                UUID.fromString(principal.getName()), id)
                                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/user/responses")
        @PreAuthorize("isAuthenticated()")
        public List<VacancyResponseDTO> getAllByUserId(Principal principal) {
                return vacancyResponseService.getAllByUserId(UUID.fromString(principal.getName()));
        }

        @GetMapping("/user/responses/{id}")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<VacancyResponseDTO> getByUserIdAndId(@PathVariable UUID id, Principal principal) {
                return vacancyResponseService.getByUserIdAndId(UUID.fromString(principal.getName()), id)
                                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @GetMapping("/vacancies/{vacancyId}/user/responses/{id}")
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<VacancyResponseDTO> getByVacancyIdAndUserIdAndId(@PathVariable UUID vacancyId,
                        @PathVariable UUID id, Principal principal) {
                return vacancyResponseService
                                .getByVacancyIdAndUserIdAndId(vacancyId, UUID.fromString(principal.getName()), id)
                                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @PostMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses")
        @PreAuthorize("isAuthenticated() && !@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<?> create(@PathVariable Long companyId, @PathVariable Long areaId,
                        @PathVariable UUID vacancyId, @Valid @RequestBody VacancyResponseCreate vacancyResponseCreate,
                        Principal principal) {
                Optional<UUID> vacancyResponseId = vacancyResponseService.create(companyId, areaId, vacancyId,
                                UUID.fromString(principal.getName()), vacancyResponseCreate);
                return vacancyResponseId.isPresent()
                                ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                                                .buildAndExpand(vacancyResponseId.get()).toUri()).build()
                                : ResponseEntity.notFound().build();
        }

        @PatchMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses/{id}")
        @PreAuthorize("isAuthenticated() && !@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<VacancyResponseDTO> partialUpdate(@PathVariable Long companyId, @PathVariable Long areaId,
                        @PathVariable UUID vacancyId, @PathVariable UUID id,
                        @RequestBody VacancyResponseCreate vacancyResponseCreate, Principal principal) {
                return vacancyResponseService.partialUpdateByCompanyIdAndAreaIdAndVacancyIdAndUserIdAndId(companyId,
                                areaId, vacancyId, UUID.fromString(principal.getName()), id, vacancyResponseCreate)
                                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @PostMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses/{id}/answer")
        @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<?> answer(@PathVariable Long companyId, @PathVariable Long areaId,
                        @PathVariable UUID vacancyId, @PathVariable UUID id,
                        @Valid @RequestBody VacancyResponseAnswer vacancyResponseAnswer) {
                return vacancyResponseService.answer(companyId, areaId, vacancyId, id, vacancyResponseAnswer)
                                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }

        @DeleteMapping("/companies/{companyId}/areas/{areaId}/vacancies/{vacancyId}/responses/{id}")
        @PreAuthorize("isAuthenticated() && !@companySecurity.isCompanyEmployer(#companyId)")
        public ResponseEntity<?> delete(@PathVariable Long companyId, @PathVariable Long areaId,
                        @PathVariable UUID vacancyId, @PathVariable UUID id) {
                return vacancyResponseService.deleteByCompanyIdAndAreaIdAndVacancyIdAndId(companyId, areaId, vacancyId,
                                id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
        }

}