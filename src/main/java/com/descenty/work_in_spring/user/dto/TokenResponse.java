package com.descenty.work_in_spring.user.dto;

public record TokenResponse(String access_token, int expires_in,
        String refresh_token, int refresh_expires_in) {
}
