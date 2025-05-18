package com.example.umerautos.dto;

import lombok.*;

import java.util.Map;

@Builder
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class LowStockEmailDTO {
    private Map<String, Integer> lowStock;
}
