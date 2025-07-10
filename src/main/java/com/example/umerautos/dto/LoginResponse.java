package com.example.umerautos.dto;

import lombok.Builder;

@Builder
public record LoginResponse(String accessToken, String refreshToken) {
}
