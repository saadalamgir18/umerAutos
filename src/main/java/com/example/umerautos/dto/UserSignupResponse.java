package com.example.umerautos.dto;

import com.example.umerautos.entities.Roles;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;

@Builder
public record UserSignupResponse(UUID id, String userName, String email, Set<Roles> role) {
}
