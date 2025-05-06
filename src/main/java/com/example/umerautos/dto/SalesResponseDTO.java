package com.example.umerautos.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SalesResponseDTO {
    private UUID id;
    private String productName;
    private int quantitySold;
    private double totalPrice;
}
