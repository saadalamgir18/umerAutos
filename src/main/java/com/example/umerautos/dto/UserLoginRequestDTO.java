package com.example.umerautos.dto;

import lombok.Builder;

@Builder
public record UserLoginRequestDTO(String email, String password) {
}
