package com.example.umerautos.services;

import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;
import com.example.umerautos.dto.PaginatedResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    ExpenseResponseDTO createOne(ExpenseRequestDTO requestDTO);
    PaginatedResponseDTO<ExpenseResponseDTO> findAll(int page, int limit);
    int todayExpense();
    int monthlyExpenses();

    ExpenseResponseDTO updateOne(ExpenseRequestDTO requestDTO, UUID id);

    ExpenseResponseDTO findOne(UUID id);
}
