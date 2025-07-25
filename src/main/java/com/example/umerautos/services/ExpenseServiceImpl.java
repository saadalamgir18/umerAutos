package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.Expenses;
import com.example.umerautos.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
    public PaginatedResponseDTO<ExpenseResponseDTO> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Expenses> expenses =   expenseRepository.findAll(pageable);
        PaginationDTO pagination = new PaginationDTO(
                expenses.getTotalElements(),
                expenses.getTotalPages(),
                page,
                limit
        );


        List<ExpenseResponseDTO> response =  expenses.stream().map(ExpenseResponseDTO::mapToDTO).toList();
        return new PaginatedResponseDTO<>(response, pagination);
    }

    @Override
    public int todayExpense() {
        return expenseRepository.todayExpense();
    }

    @Override
    public int monthlyExpenses() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime today = LocalDateTime.now();

        Timestamp start = Timestamp.valueOf(startOfMonth);
        Timestamp end = Timestamp.valueOf(today);
        return expenseRepository.getMonthlyExpense(start, end);

    }

    @Override
    public ExpenseResponseDTO updateOne(ExpenseRequestDTO requestDTO, UUID id) {
        Optional<Expenses> expenses = expenseRepository.findById(id);
        if (expenses.isPresent()){
            expenses.get().setAmount(requestDTO.amount());
            expenses.get().setDescription(requestDTO.description());
           Expenses updatedExpense =  expenseRepository.save(expenses.get());
            return ExpenseResponseDTO.mapToDTO(updatedExpense);
        }
        return null;
    }

    @Override
    public ExpenseResponseDTO findOne(UUID id) {
        Optional<Expenses> expenses = expenseRepository.findById(id);
        if (expenses.isPresent()){
            return ExpenseResponseDTO.mapToDTO(expenses.get());
        }

        return null;
    }
}
