package com.example.umerautos.services;

import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;
import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

public interface ExpenseService {

    ExpenseResponseDTO createOne(ExpenseRequestDTO requestDTO);
    List<ExpenseResponseDTO> findAll();
    double todayExpense();
    double monthlyExpenses();

    ExpenseResponseDTO updateOne(ExpenseRequestDTO requestDTO, UUID id);

    ExpenseResponseDTO findOne(UUID id);
}
