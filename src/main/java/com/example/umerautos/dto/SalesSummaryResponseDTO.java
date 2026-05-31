package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.SalesSummary;
import lombok.Builder;

import java.util.Date;
import java.util.List;

@Builder
public record SalesSummaryResponseDTO(
        Long id,
        CustomerResponseDto customer,
        double totalAmountSummary,

        int quantitySoldSummary,
        List<SalesResponseDTO> saleItems,

        PaymentStatus paymentStatus,
        Date createdAt
) {


    public static SalesSummaryResponseDTO mapToDTO(SalesSummary salesSummary) {
        List<SalesResponseDTO> salesDTOs = salesSummary.getSaleItems().stream()
                .map(sale -> SalesResponseDTO.builder()
                        .productId(sale.getProduct().getId())
                        .id(sale.getId())
                        .productName(sale.getProduct().getName())
                        .quantitySold(sale.getQuantitySold())
                        .totalPrice(sale.getTotalAmount())
                        .createdAt(sale.getCreatedAt())
                        .paymentStatus(sale.getPaymentStatus())
                        .build()
                )
                .toList();
        return SalesSummaryResponseDTO
                .builder()
                .customer(salesSummary.getCustomer() != null ? CustomerResponseDto.mapToDto(salesSummary.getCustomer()) : null)
                .quantitySoldSummary(salesSummary.getQuantitySold())
                .totalAmountSummary(salesSummary.getTotalAmount())
                .id(salesSummary.getId())
                .paymentStatus(salesSummary.getPaymentStatus())
                .saleItems(salesDTOs)
                .createdAt(salesSummary.getCreatedAt())
                .build();

    }

}
