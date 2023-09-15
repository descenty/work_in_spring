package com.descenty.work_in_spring.user.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @GetMapping("/vacancies/{vacancyId}/responses")
    @PreAuthorize("hasAuthority('employer')")
    public List<VacancyResponseDTO> getAll(UUID vacancyId) {
        return vacancyResponseService.getAllByVacancyId(vacancyId);
    }

    @GetMapping("/vacancies/{vacancyId}/responses/{id}")
    @PreAuthorize("hasAuthority('employer')")
    public ResponseEntity<VacancyResponseDTO> getById(UUID vacancyId, UUID id) {
        return vacancyResponseService.getById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/vacancies/{vacancyId}/responses")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> create(UUID vacancyId, VacancyResponseCreate vacancyResponseCreate) {
        Optional<UUID> vacancyResponseId = vacancyResponseService.create(vacancyId, vacancyResponseCreate);
        return vacancyResponseId.isPresent() ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}").buildAndExpand(vacancyResponseId.get()).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/vacancies/{vacancyId}/responses/{id}/accept")
    @PreAuthorize("hasAuthority('employer')")
    public ResponseEntity<?> accept(UUID vacancyId, UUID id) {
        return vacancyResponseService.setStatus(id, Status.ACCEPTED) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/vacancies/{vacancyId}/responses/{id}/reject")
    @PreAuthorize("hasAuthority('employer')")
    public ResponseEntity<?> reject(UUID vacancyId, UUID id) {
        return vacancyResponseService.setStatus(id, Status.REJECTED) ? ResponseEntity.ok().build()
                : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/vacancies/{vacancyId}/responses/{id}")
    @PreAuthorize("hasAuthority('employer')")
    public ResponseEntity<?> delete(UUID vacancyId, UUID id) {
        return vacancyResponseService.delete(id) ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }

}