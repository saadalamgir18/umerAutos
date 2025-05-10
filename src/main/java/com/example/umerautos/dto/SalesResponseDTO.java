package com.example.umerautos.dto;

import com.example.umerautos.entities.Sales;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SalesResponseDTO {
    private UUID productId;
    private String productName;
    private int quantitySold;
    private double totalPrice;
    private double profit;

    public static SalesResponseDTO mapToDTO(Sales sales){
        return SalesResponseDTO
                .builder()
                .productId( sales.getId())
                .productName(sales.getProduct().getName())
                .quantitySold(sales.getQuantitySold())
                .totalPrice(sales.getTotalAmount())
                .build();
    }
}
