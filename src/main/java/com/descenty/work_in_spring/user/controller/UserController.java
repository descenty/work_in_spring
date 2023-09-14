package com.descenty.work_in_spring.user.controller;

import com.descenty.work_in_spring.company.dto.CompanyCreate;
import com.descenty.work_in_spring.keycloak.dto.LoginRequest;
import com.descenty.work_in_spring.keycloak.dto.SignUpRequest;
import com.descenty.work_in_spring.keycloak.service.KeycloakService;
import com.descenty.work_in_spring.user.dto.UserCreate;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final KeycloakService keycloakService;

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.status(201)
                .body(keycloakService.createKeycloakUser(signUpRequest));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(
            @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(keycloakService.login(loginRequest));
    }
}
