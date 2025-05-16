package com.example.umerautos.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SaleUpdateRequestDTO {

    @NotNull(message = "quantity must not be null")
    private  int quantitySold;

    @NotNull(message = "amount must not be null")
    private double totalAmount;

}
