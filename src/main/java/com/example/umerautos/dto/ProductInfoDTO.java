package com.example.umerautos.dto;

import lombok.Builder;
import lombok.Data;

@Builder
public record ProductInfoDTO(Long id, String productName, String sku) {
}
