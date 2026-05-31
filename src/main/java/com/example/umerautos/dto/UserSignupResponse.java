package com.example.umerautos.dto;

import com.example.umerautos.entities.Roles;
import lombok.Builder;

import java.util.Set;

@Builder
public record UserSignupResponse(
        String userName,
        String email,
        Set<Roles> roles,
        Long id
) {
}
