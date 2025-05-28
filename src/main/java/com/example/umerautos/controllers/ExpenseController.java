package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.ExpenseRequestDTO;
import com.example.umerautos.dto.ExpenseResponseDTO;
import com.example.umerautos.services.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ExpenseController {

    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public ResponseEntity<?> getAll(){
        try {

            List<ExpenseResponseDTO> responseDTOS = expenseService.findAll();
            if (responseDTOS.isEmpty()){
                return CustomResponse.generateResponse(HttpStatus.OK, true, "success", new ArrayList<>());

            }
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", responseDTOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @PostMapping("/expenses")
    public ResponseEntity<?> saveOne(@Valid  @RequestBody ExpenseRequestDTO requestDTO){
        try {

            ExpenseResponseDTO responseDTOS = expenseService.createOne(requestDTO);

            if (responseDTOS.getId() == null){

                throw new RuntimeException();
            }

            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", responseDTOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/expenses/{id}")
    public ResponseEntity<?> findOne(@PathVariable UUID id){
        try {

            ExpenseResponseDTO responseDTOS = expenseService.findOne(id);

            if (responseDTOS == null){

                throw new RuntimeException();
            }

            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", responseDTOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }



    @PutMapping("/expenses/{id}")
    public ResponseEntity<?> updateOne(@Valid  @RequestBody ExpenseRequestDTO requestDTO, @PathVariable UUID id){
        try {

            ExpenseResponseDTO responseDTOS = expenseService.updateOne(requestDTO, id);

            if (responseDTOS == null){

                throw new RuntimeException();
            }

            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", responseDTOS);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/expenses/today")
    public ResponseEntity<?> getToDayExpense(){
        try {

            double todayExpense  = expenseService.todayExpense();
            System.out.println("todayExpense: "+todayExpense);

            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", todayExpense);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    @GetMapping("/expenses/monthly")
    public ResponseEntity<?> getMonthlyExpense(){
        try {

            double monthlyExpense  = expenseService.monthlyExpenses();

            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", monthlyExpense);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
