package com.example.umerautos.controllers;

import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;
import com.example.umerautos.services.ExpenseService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class ExpenseController {

    private final ExpenseService expenseService;
    private static final Logger logger = LoggerFactory.getLogger(ExpenseController.class);

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping("/expenses")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit
    ) {
        logger.info("Request received to fetch all expenses. Page: {}, Limit: {}", page, limit);
        try {
            var responseDTOS = expenseService.findAll(page, limit);
            logger.info("Successfully fetched expenses");
            return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching expenses", e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/expenses")
    public ResponseEntity<?> saveOne(@Valid @RequestBody ExpenseRequestDTO requestDTO) {
        logger.info("Request received to save expense: {}", requestDTO.description());
        try {
            ExpenseResponseDTO responseDTOS = expenseService.createOne(requestDTO);

            if (responseDTOS.id() == null) {
                logger.error("Failed to save expense: {}", requestDTO.description());
                throw new RuntimeException("Failed to save expense");
            }

            logger.info("Expense saved successfully with ID: {}", responseDTOS.id());
            return new ResponseEntity<>(responseDTOS, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving expense: {}", requestDTO.description(), e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        logger.info("Request received to fetch expense with ID: {}", id);
        try {
            ExpenseResponseDTO responseDTOS = expenseService.findOne(id);

            if (responseDTOS == null) {
                logger.warn("Expense not found with ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            logger.info("Successfully fetched expense with ID: {}", id);
            return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching expense with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/expenses/{id}")
    public ResponseEntity<?> updateOne(@Valid @RequestBody ExpenseRequestDTO requestDTO, @PathVariable Long id) {
        logger.info("Request received to update expense with ID: {}", id);
        try {
            ExpenseResponseDTO responseDTOS = expenseService.updateOne(requestDTO, id);

            if (responseDTOS == null) {
                logger.warn("Failed to update expense. ID not found: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
            logger.info("Successfully updated expense with ID: {}", id);
            return new ResponseEntity<>(responseDTOS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error updating expense with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/expenses/today")
    public ResponseEntity<?> getToDayExpense() {
        logger.info("Request received to fetch today's expenses");
        try {
            double todayExpense = expenseService.todayExpense();
            logger.info("Successfully fetched today's expenses: {}", todayExpense);
            return new ResponseEntity<>(todayExpense, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching today's expenses", e);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/expenses/monthly")
    public ResponseEntity<?> getMonthlyExpense() {
        logger.info("Request received to fetch monthly expenses");
        try {
            double monthlyExpense = expenseService.monthlyExpenses();
            logger.info("Successfully fetched monthly expenses: {}", monthlyExpense);
            return new ResponseEntity<>(monthlyExpense, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching monthly expenses", e);
            throw new RuntimeException(e);
        }
    }
}
