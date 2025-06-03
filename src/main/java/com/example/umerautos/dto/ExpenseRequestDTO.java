package com.example.umerautos.dto;

import com.example.umerautos.entities.Expenses;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
public record ExpenseRequestDTO(
        @NotBlank(message = "brand name should not be null")
        @Size(min = 3, message = "name size must be greater then 3")
         String description,
        @NotNull(message = "brand name should not be null")
        @Min(value = 1)
        int amount

) {




    public static Expenses mapToDTO(ExpenseRequestDTO expenses){
        return Expenses
                .builder()
                .amount(expenses.amount())
                .description(expenses.description())
                .build();
    }
}
