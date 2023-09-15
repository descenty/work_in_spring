package com.descenty.work_in_spring.user.controller;

import com.descenty.work_in_spring.user.dto.LoginRequest;
import com.descenty.work_in_spring.user.dto.SignUpRequest;
import com.descenty.work_in_spring.user.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<?> create(@RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.status(201)
                .body(userService.createUser(signUpRequest));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser(
            @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.login(loginRequest));
    }
}
