package com.example.umerautos.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class ProductInfoDTO {
    private UUID id;
    private String productName;
    private String sku;
}
