package com.descenty.work_in_spring.company.controller;

import com.descenty.work_in_spring.company.service.CompanyService;
import com.descenty.work_in_spring.company.dto.CompanyCreate;
import com.descenty.work_in_spring.company.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("")
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
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public ResponseEntity<?> create(@PathVariable Long areaId, @RequestBody CompanyCreate companyCreate, @AuthenticationPrincipal UserDetails userDetails) {
        /* TODO if ADMIN, then just create,
            else if EMPLOYER, create kafka task for moderators to approve company creation info,
            when moderator approves, he creates company and adds employer to companyAdmins*/
        return companyService.create(areaId, companyCreate).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public ResponseEntity<CompanyDTO> update(@PathVariable Long id, @RequestBody CompanyCreate companyCreate) {
        /* TODO if ADMIN, then just update,
            else if EMPLOYER, check that company with id equal to path id has this employer in companyAdmins,
            then create kafka task for moderators to approve company update info */
        return companyService.update(id, companyCreate).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/companies/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYER')")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        /* TODO if ADMIN, then just delete,
            else if EMPLOYER, check that company with id equal to path id has this employer in companyAdmins */
        return companyService.delete(id) ? ResponseEntity.ok(id) : ResponseEntity.notFound().build();
    }

    // TODO use kotlin microservice instead
//    @PostMapping("/{id}/upload-logo")
//    public ResponseEntity<?> uploadLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
//        return companyService.uploadLogo(id, file).map(ResponseEntity::ok).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
//    }

}