package com.descenty.work_in_spring.user.controller;

import com.descenty.work_in_spring.user.dto.AuthRequest;
import com.descenty.work_in_spring.user.dto.Role;
import com.descenty.work_in_spring.user.dto.TokenResponse;
import com.descenty.work_in_spring.user.service.UserService;

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> create(@RequestBody AuthRequest authRequest) {
        String createUserResponse = userService.createUser(authRequest);
        try {
            UUID userId = UUID.fromString(createUserResponse);
            return ResponseEntity.created(
                    ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(userId).toUri())
                    .build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        Optional<TokenResponse> tokenResponse = userService.authenticate(authRequest);
        return tokenResponse.isPresent() ? ResponseEntity.ok(tokenResponse.get()) : ResponseEntity.status(401).build();
    }

    @PostMapping("/{userID}/roles")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> addRoles(@PathVariable UUID userID, @RequestBody Role[] rolesRequest) {
        return ResponseEntity.ok(userService.addRoles(userID, rolesRequest));
    }

    @DeleteMapping("/{userID}/roles")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<?> removeRoles(@PathVariable UUID userID, @RequestBody Role[] rolesRequest) {
        return ResponseEntity.ok(userService.removeRoles(userID, rolesRequest));
    }
}
