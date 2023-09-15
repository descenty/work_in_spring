package com.descenty.work_in_spring.company.controller;

import com.descenty.work_in_spring.company.service.CompanyService;

import jakarta.validation.Valid;

import com.descenty.work_in_spring.company.dto.CompanyCreate;
import com.descenty.work_in_spring.company.dto.CompanyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;

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
    @PreAuthorize("hasAuthority('admin') or hasAuthority('employer')")
    public ResponseEntity<?> create(@PathVariable Long areaId, @Valid @RequestBody CompanyCreate companyCreate,
            @AuthenticationPrincipal UserDetails userDetails) {
        Optional<Long> companyId = companyService.create(areaId, companyCreate);
        return companyId.isPresent()
                ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(companyId.get()).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/companies/{id}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('employer')")
    public ResponseEntity<CompanyDTO> update(@PathVariable Long id, @Valid @RequestBody CompanyCreate companyCreate) {
        return companyService.update(id, companyCreate).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/companies/{id}")
    @PreAuthorize("hasAuthority('admin') or hasAuthority('employer')")
    public ResponseEntity<Long> delete(@PathVariable Long id) {
        return companyService.delete(id) ? ResponseEntity.ok(id) : ResponseEntity.notFound().build();
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