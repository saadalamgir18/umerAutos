package com.example.umerautos.dto;

import com.example.umerautos.entities.Roles;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record UserUpdateRequest(
        @Pattern(regexp = "^(ADMIN|USER)", message = "The role should be ADMIN | USER") @NotNull(message = "The role can not be null") Roles roles) {
}
