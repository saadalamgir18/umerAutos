package com.example.umerautos.dto;

import com.example.umerautos.entities.Expenses;
import lombok.*;

import java.util.Date;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ExpenseResponseDTO {
    private UUID id;
    protected Date createdAt;

    private String description;
    private double amount;

    public static ExpenseResponseDTO mapToDTO(Expenses expenses){
        return ExpenseResponseDTO
                .builder()
                .amount(expenses.getAmount())
                .description(expenses.getDescription())
                .id(expenses.getId())
                .createdAt(expenses.getCreatedAt())
                .build();
    }
}
