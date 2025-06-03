package com.example.umerautos.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
public record ProductInfoDTO(UUID id, String productName, String sku) {
}
