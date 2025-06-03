package com.example.umerautos.dto;

import lombok.Builder;

import java.util.Map;

@Builder

public record LowStockEmailDTO(Map<String, Integer> lowStock) {
}
