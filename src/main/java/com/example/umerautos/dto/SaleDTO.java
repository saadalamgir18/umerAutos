package com.example.umerautos.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SaleDTO {
    private UUID productId;
    private  int quantitySold;
    private double totalAmount;
}
