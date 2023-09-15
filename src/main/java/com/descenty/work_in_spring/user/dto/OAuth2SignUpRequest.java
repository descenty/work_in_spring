package com.descenty.work_in_spring.user.dto;

public record OAuth2SignUpRequest(String email, boolean enabled,
        Credential[] credentials) {
}
