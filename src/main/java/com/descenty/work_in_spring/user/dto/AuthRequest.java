package com.descenty.work_in_spring.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {
    String username;
    String password;
    String refreshToken;
}
