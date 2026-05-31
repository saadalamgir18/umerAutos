package com.example.umerautos.services;

import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;
import com.example.umerautos.dto.PaginatedResponseDTO;

public interface ExpenseService {

    ExpenseResponseDTO createOne(ExpenseRequestDTO requestDTO);

    PaginatedResponseDTO<ExpenseResponseDTO> findAll(int page, int limit);

    int todayExpense();

    int monthlyExpenses();

    ExpenseResponseDTO updateOne(ExpenseRequestDTO requestDTO, Long id);

    ExpenseResponseDTO findOne(Long id);
}
