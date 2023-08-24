package com.descenty.work_in_spring.vacancy.controller;

import com.descenty.work_in_spring.vacancy.service.VacancyService;
import com.descenty.work_in_spring.vacancy.dto.VacancyCreate;
import com.descenty.work_in_spring.vacancy.dto.VacancyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
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
    public ResponseEntity<VacancyDTO> getById(@PathVariable Long areaId, @PathVariable Long companyId, @PathVariable UUID id) {
        return vacancyService.getById(areaId, companyId, id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/areas/{areaId}/companies/{companyId}/vacancies")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public VacancyDTO create(@PathVariable Long areaId, @PathVariable Long companyId, @RequestBody VacancyCreate vacancyCreate) {
        /* TODO if ADMIN, then just create,
            else if EMPLOYER, create kafka task for moderators to approve vacancy creation info:
             check that empoyer has a company with id equal to companyId*/
        return vacancyService.create(areaId, companyId, vacancyCreate).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PatchMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public ResponseEntity<VacancyDTO> update(@PathVariable Long areaId, @PathVariable Long companyId, @PathVariable UUID
            id, @RequestBody VacancyCreate vacancyCreate) {
        /* TODO if ADMIN, then just update,
            else if EMPLOYER, create kafka task for moderators to approve vacancy update info:
             check that empoyer has a company with id equal to companyId*/
        return vacancyService.update(areaId, companyId, id, vacancyCreate).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/areas/{areaId}/companies/{companyId}/vacancies/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public ResponseEntity<?> delete(@PathVariable Long areaId, @PathVariable Long companyId, @PathVariable UUID id) {
        /* TODO if ADMIN, then just delete,
            else if EMPLOYER, check that empoyer has a company with id equal to companyId*/
        return vacancyService.delete(areaId, companyId, id) ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }
}