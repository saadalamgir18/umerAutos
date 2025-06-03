package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


@Builder
public record SalesSummaryRequestDTO(
        String customerName,

        @Enumerated(value = EnumType.STRING)
        PaymentStatus paymentStatus,

        @NotNull(message = "amount must not be null")
        int totalAmountSummary,

        @NotNull(message = "quantity must not be null")
        int quantitySoldSummary,

        @NotEmpty(message = "sale item must not be empty")
        List<SaleDTO> saleItems
) {


}
