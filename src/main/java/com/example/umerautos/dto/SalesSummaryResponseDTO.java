package com.example.umerautos.dto;

import com.example.umerautos.entities.BaseModel;
import com.example.umerautos.entities.SalesSummary;
import lombok.*;

import java.util.Date;
import java.util.UUID;

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

    public static SalesSummaryResponseDTO mapToDTO(SalesSummary salesSummary){
        return  SalesSummaryResponseDTO
                .builder()
                .customerName(salesSummary.getCustomerName())
                .quantitySoldSummary(salesSummary.getQuantitySold())
                .totalAmountSummary(salesSummary.getTotalAmount())
                .Id(salesSummary.getId())
                .build();

    }

}
