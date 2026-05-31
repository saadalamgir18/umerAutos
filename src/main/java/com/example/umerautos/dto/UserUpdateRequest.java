package com.example.umerautos.dto;

import com.example.umerautos.entities.Roles;

public record UserUpdateRequest(
        String userName,
        String email,
        Roles roles
) {
}
