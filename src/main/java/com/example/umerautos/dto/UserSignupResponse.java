package com.example.umerautos.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserSignupResponse(UUID id, String userName, String email, String role) {
}
