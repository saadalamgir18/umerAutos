package com.example.umerautos.dto;

import lombok.Builder;

@Builder
public record UserSignupResponse(String userName, String email, String role) {
}
