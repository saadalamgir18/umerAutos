package com.example.umerautos.dto;

import com.example.umerautos.entities.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SalesSummaryRequestDTO {
    private Long customerId;
    private PaymentStatus paymentStatus;
    private double totalAmountSummary;
    private int quantitySoldSummary;
    private List<SaleItemDTO> saleItems;

    @Getter
    @Setter
    public static class SaleItemDTO {
        private Long productId;
        private int quantitySold;
        private double totalAmount;
    }
}
