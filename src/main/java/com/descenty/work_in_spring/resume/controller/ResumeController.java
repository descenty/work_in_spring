package com.descenty.work_in_spring.resume.controller;

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

import com.descenty.work_in_spring.resume.dto.ResumeCreate;
import com.descenty.work_in_spring.resume.dto.ResumeDTO;
import com.descenty.work_in_spring.resume.service.ResumeService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class ResumeController {
    private final ResumeService resumeService;

    @GetMapping("/resumes")
    @PreAuthorize("isAuthenticated()")
    public List<ResumeDTO> getAllByUserId(Principal principal) {
        return resumeService.getAllByUserId(UUID.fromString(principal.getName()));
    }

    @GetMapping("/resumes/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResumeDTO> getByUserIdAndId(@PathVariable UUID id, Principal principal) {
        return resumeService.getByUserIdAndId(UUID.fromString(principal.getName()), id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/resumes")
    @PreAuthorize("hasAuthority('admin')")
    public List<ResumeDTO> getAllByUserId(@PathVariable UUID userId) {
        return resumeService.getAllByUserId(userId);
    }

    @GetMapping("/{userId}/resumes/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<ResumeDTO> getByUserId(@PathVariable UUID userId, @PathVariable UUID id) {
        return resumeService.getByUserIdAndId(userId, id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/resumes")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResumeDTO> create(@Valid @RequestBody ResumeCreate resumeCreate, Principal principal) {
        Optional<UUID> resumeId = resumeService.create(UUID.fromString(principal.getName()), resumeCreate);
        return resumeId.isPresent()
                ? ResponseEntity.created(ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                        .buildAndExpand(resumeId.get()).toUri()).build()
                : ResponseEntity.notFound().build();
    }

    @PatchMapping("/resumes/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ResumeDTO> partialUpdate(@PathVariable UUID id, @Valid @RequestBody ResumeCreate resumeCreate,
            Principal principal) {
        return resumeService.update(UUID.fromString(principal.getName()), id, resumeCreate).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/resumes/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> delete(@PathVariable UUID id, Principal principal) {
        if (resumeService.deleteByUserIdAndId(UUID.fromString(principal.getName()), id))
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }
}
