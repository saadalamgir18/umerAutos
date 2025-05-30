package com.example.umerautos.dto;

import com.example.umerautos.entities.BaseModel;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.entities.SalesSummary;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SalesSummaryResponseDTO {
    protected UUID Id;

    private String customerName;
    private double totalAmountSummary;

    private  int quantitySoldSummary;
    private List<SalesResponseDTO> saleItems;

    private PaymentStatus paymentStatus;
    protected Date createdAt;


    public static SalesSummaryResponseDTO mapToDTO(SalesSummary salesSummary){
        List<SalesResponseDTO> salesDTOs = salesSummary.getSaleItems().stream()
                .map(sale -> SalesResponseDTO.builder()
                        .productId(sale.getId())
                        .productName(sale.getProduct().getName())
                        .quantitySold(sale.getQuantitySold())
                        .totalPrice(sale.getTotalAmount())
                        .createdAt(sale.getCreatedAt())
                        .build()
                )
                .toList();
        return  SalesSummaryResponseDTO
                .builder()
                .customerName(salesSummary.getCustomerName())
                .quantitySoldSummary(salesSummary.getQuantitySold())
                .totalAmountSummary(salesSummary.getTotalAmount())
                .Id(salesSummary.getId())
                .paymentStatus(salesSummary.getPaymentStatus())
                .saleItems(salesDTOs)
                .createdAt(salesSummary.getCreatedAt())
                .build();

    }

}
