package com.example.umerautos.dto;

import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SalesUpdateResponseDTO {
    private UUID id;
    private ProductInfoDTO product;
    private int quantitySold;
    private double totalPrice;

    public static SalesUpdateResponseDTO mapToDTO(Sales sales){
        return SalesUpdateResponseDTO
                .builder()
                .id(sales.getId())
                .quantitySold(sales.getQuantitySold())
                .totalPrice(sales.getTotalAmount())
                .build();
    }
}
