package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SalesSummaryRequestDTO {


    private String customerName;

    @Enumerated(value = EnumType.STRING)
    private PaymentStatus paymentStatus;

    @NotNull(message = "amount must not be null")
    private int totalAmountSummary;

    @NotNull(message = "quantity must not be null")
    private  int quantitySoldSummary;

    @NotEmpty(message = "sale item must not be empty")
    private List<SaleDTO> saleItems;

}
