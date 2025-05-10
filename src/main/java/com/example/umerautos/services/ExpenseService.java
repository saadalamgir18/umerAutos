package com.example.umerautos.services;

import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDTO createOne(ExpenseRequestDTO requestDTO);
    List<ExpenseResponseDTO> findAll();
    double todayExpense();
    double monthlyExpenses();

}
