package com.example.umerautos.services;

import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;
import com.example.umerautos.entities.Expenses;
import com.example.umerautos.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired private ExpenseRepository expenseRepository;
    @Override
    public ExpenseResponseDTO createOne(ExpenseRequestDTO requestDTO) {
        Expenses expenses = ExpenseRequestDTO.mapToDTO(requestDTO);
        Expenses newExpenses = expenseRepository.save(expenses);
        return ExpenseResponseDTO.mapToDTO(newExpenses);
    }

    @Override
    public List<ExpenseResponseDTO> findAll() {
        List<Expenses> expenses =   expenseRepository.findAll();
        return expenses.stream().map(ExpenseResponseDTO::mapToDTO).toList();
    }

    @Override
    public double todayExpense() {
        return expenseRepository.todayExpense();
    }

    @Override
    public double monthlyExpenses() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime today = LocalDateTime.now();

        Timestamp start = Timestamp.valueOf(startOfMonth);
        Timestamp end = Timestamp.valueOf(today);
        return expenseRepository.getMonthlyExpense(start, end);

    }
}
