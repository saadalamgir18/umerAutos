package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import lombok.*;

@Builder
public record SalesUpdateResponseDTO(
         Long id,
         ProductInfoDTO product,
         int quantitySold,
         double totalPrice,
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
