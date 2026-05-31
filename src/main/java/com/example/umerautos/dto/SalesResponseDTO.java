package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Sales;
import lombok.Builder;

import java.util.Date;


@Builder
public record SalesResponseDTO(
        Long id,
        Long productId,
        String productName,
        int quantitySold,
        double totalPrice,
        int profit,
        Date createdAt,
        PaymentStatus paymentStatus
) {

    public static SalesResponseDTO mapToDTO(Sales sales) {
        return SalesResponseDTO
                .builder()
                .id(sales.getId())
                .productId(sales.getProduct().getId())
                .productName(sales.getProduct().getName())
                .quantitySold(sales.getQuantitySold())
                .totalPrice(sales.getTotalAmount())
                .paymentStatus(sales.getPaymentStatus())
                .build();
    }
}
