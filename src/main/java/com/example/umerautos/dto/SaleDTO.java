package com.example.umerautos.dto;

import lombok.*;

import java.util.UUID;

@Builder
public record SaleDTO(UUID productId, int quantitySold, int totalAmount) {
}
