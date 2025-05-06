package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    private double totalAmountSummary;

    private  double quantitySoldSummary;

    private List<SaleDTO> saleItems;

}
