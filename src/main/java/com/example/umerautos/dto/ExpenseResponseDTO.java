package com.example.umerautos.dto;

import com.example.umerautos.entities.Expenses;
import lombok.*;

import java.util.Date;

@Builder
public record ExpenseResponseDTO(
        Long id, Date createdAt, String description, int amount
) {

    public static ExpenseResponseDTO mapToDTO(Expenses expenses) {
        return ExpenseResponseDTO
                .builder()
                .amount(expenses.getAmount())
                .description(expenses.getDescription())
                .id(expenses.getId())
                .createdAt(expenses.getCreatedAt())
                .build();
    }
}
