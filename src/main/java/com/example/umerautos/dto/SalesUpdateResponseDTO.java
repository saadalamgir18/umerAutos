package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import lombok.*;

import java.util.UUID;

@Builder
public record SalesUpdateResponseDTO(
         UUID id,
         ProductInfoDTO product,
         int quantitySold,
         int totalPrice,
         PaymentStatus paymentStatus
) {


    public static SalesUpdateResponseDTO mapToDTO(Sales sales){
        return SalesUpdateResponseDTO
                .builder()
                .id(sales.getId())
                .quantitySold(sales.getQuantitySold())
                .totalPrice(sales.getTotalAmount())
                .build();

    }
}
