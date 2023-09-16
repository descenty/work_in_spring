package com.descenty.work_in_spring.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.actuate.web.exchanges.HttpExchange.Principal;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.descenty.work_in_spring.user.dto.VacancyResponseCreate;
import com.descenty.work_in_spring.user.dto.VacancyResponseDTO;
import com.descenty.work_in_spring.user.entity.VacancyResponse.Status;
import com.descenty.work_in_spring.user.service.VacancyResponseService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class VacancyResponseController {
    private final VacancyResponseService vacancyResponseService;

    @GetMapping("/companies/{companyId}/vacancies/{vacancyId}/responses")
    @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
    public List<VacancyResponseDTO> getAllByCompanyIdAndVacancyId(Long companyId, UUID vacancyId, Principal principal) {
        return vacancyResponseService.getAllByCompanyIdAndVacancyId(companyId, vacancyId,
                UUID.fromString(principal.getName()));
    }

    @GetMapping("/companies/{companyId}/vacancies/{vacancyId}/responses/{id}")
    @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<VacancyResponseDTO> getByCompanyIdAndVacancyIdAndId(Long companyId, UUID vacancyId, UUID id,
            Principal principal) {
        return vacancyResponseService
                .getByCompanyIdAndVacancyIdAndId(companyId, vacancyId, id, UUID.fromString(principal.getName()))
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/responses")
    @PreAuthorize("isAuthenticated()")
    public List<VacancyResponseDTO> getAllByUserId(Principal principal) {
        return vacancyResponseService.getAllByUserId(UUID.fromString(principal.getName()));
    }

    @GetMapping("/user/responses/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VacancyResponseDTO> getByUserIdAndId(UUID id, Principal principal) {
        return vacancyResponseService.getByUserIdAndId(UUID.fromString(principal.getName()), id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/vacancies/{vacancyId}/user/responses/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<VacancyResponseDTO> getByVacancyIdAndUserIdAndId(UUID vacancyId, UUID id,
            Principal principal) {
        return vacancyResponseService.getByVacancyIdAndUserIdAndId(vacancyId, UUID.fromString(principal.getName()), id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/companies/{companyId/vacancies/{vacancyId}/responses")
    @PreAuthorize("!@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<?> create(Long companyId, UUID vacancyId, VacancyResponseCreate vacancyResponseCreate) {
        Optional<UUID> vacancyResponseId = vacancyResponseService.create(companyId, vacancyId, vacancyResponseCreate);
        return vacancyResponseId.isPresent() ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(vacancyResponseId.get()).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/companies/{companyId}/vacancies/{vacancyId}/responses/{id}/accept")
    @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<?> accept(Long companyId, UUID vacancyId, UUID id) {
        return vacancyResponseService.setStatus(companyId, vacancyId, id, Status.ACCEPTED) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/companies/{companyId}/vacancies/{vacancyId}/responses/{id}/reject")
    @PreAuthorize("@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<?> reject(Long companyId, UUID vacancyId, UUID id) {
        return vacancyResponseService.setStatus(companyId, vacancyId, id, Status.REJECTED) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/companies/{companyId}/vacancies/{vacancyId}/responses/{id}")
    @PreAuthorize("!@companySecurity.isCompanyEmployer(#companyId)")
    public ResponseEntity<?> delete(Long companyId, UUID vacancyId, UUID id) {
        return vacancyResponseService.deleteByVacancyIdAndId(vacancyId, id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}