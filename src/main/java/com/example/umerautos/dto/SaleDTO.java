package com.example.umerautos.dto;

import lombok.*;

@Builder
public record SaleDTO(Long productId, int quantitySold, int totalAmount) {
}
