package com.example.umerautos.dto;

import com.example.umerautos.entities.Expenses;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExpenseRequestDTO {
    private String description;
    private double amount;

    public static Expenses mapToDTO(ExpenseRequestDTO expenses){
        return Expenses
                .builder()
                .amount(expenses.getAmount())
                .description(expenses.getDescription())
                .build();
    }
}
