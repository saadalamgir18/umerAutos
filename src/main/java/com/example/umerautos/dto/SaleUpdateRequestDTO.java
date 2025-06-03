package com.example.umerautos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
public record SaleUpdateRequestDTO(
        @NotNull(message = "quantity must not be null")
        int quantitySold,

        @NotNull(message = "amount must not be null")
        int totalAmount
) {
}
