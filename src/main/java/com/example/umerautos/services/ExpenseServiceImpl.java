package com.example.umerautos.services;

import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;
import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.PaginationDTO;
import com.example.umerautos.entities.Expenses;
import com.example.umerautos.repositories.ExpenseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseServiceImpl.class);

    @Override
    public ExpenseResponseDTO createOne(ExpenseRequestDTO requestDTO) {
        logger.info("Creating new expense: {}", requestDTO.description());
        Expenses expenses = ExpenseRequestDTO.mapToDTO(requestDTO);
        Expenses newExpenses = expenseRepository.save(expenses);
        logger.info("Expense created with id: {}", newExpenses.getId());
        return ExpenseResponseDTO.mapToDTO(newExpenses);
    }

    @Override
    public PaginatedResponseDTO<ExpenseResponseDTO> findAll(int page, int limit) {
        logger.info("Fetching all expenses. Page: {}, Limit: {}", page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Expenses> expenses = expenseRepository.findAll(pageable);
        logger.info("Found {} expenses", expenses.getTotalElements());
        PaginationDTO pagination = new PaginationDTO(
                expenses.getTotalElements(),
                expenses.getTotalPages(),
                page,
                limit
        );


        List<ExpenseResponseDTO> response = expenses.stream().map(ExpenseResponseDTO::mapToDTO).toList();
        return new PaginatedResponseDTO<>(response, pagination);
    }

    @Override
    public int todayExpense() {
        logger.info("Calculating today's expense");
        int expense = expenseRepository.todayExpense();
        logger.info("Today's expense: {}", expense);
        return expense;
    }

    @Override
    public int monthlyExpenses() {
        logger.info("Calculating monthly expenses");
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime today = LocalDateTime.now();

        Timestamp start = Timestamp.valueOf(startOfMonth);
        Timestamp end = Timestamp.valueOf(today);
        int expense = expenseRepository.getMonthlyExpense(start, end);
        logger.info("Monthly expense: {}", expense);
        return expense;

    }

    @Override
    public ExpenseResponseDTO updateOne(ExpenseRequestDTO requestDTO, Long id) {
        logger.info("Updating expense with id: {}", id);
        Optional<Expenses> expenses = expenseRepository.findById(id);
        if (expenses.isPresent()) {
            expenses.get().setAmount(requestDTO.amount());
            expenses.get().setDescription(requestDTO.description());
            Expenses updatedExpense = expenseRepository.save(expenses.get());
            logger.info("Expense updated successfully");
            return ExpenseResponseDTO.mapToDTO(updatedExpense);
        }
        logger.warn("Expense not found for update with id: {}", id);
        return null;
    }

    @Override
    public ExpenseResponseDTO findOne(Long id) {
        logger.info("Fetching expense with id: {}", id);
        Optional<Expenses> expenses = expenseRepository.findById(id);
        if (expenses.isPresent()) {
            logger.info("Expense found: {}", expenses.get().getDescription());
            return ExpenseResponseDTO.mapToDTO(expenses.get());
        }
        logger.warn("Expense not found with id: {}", id);
        return null;
    }
}
