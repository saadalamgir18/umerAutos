package com.example.umerautos.controllers;

import com.example.umerautos.dto.SalesSummaryRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class SalesSummaryController {
    @PostMapping("/sales-summary")
    public ResponseEntity<?> saveSales(@RequestBody SalesSummaryRequestDTO salesRequestDTO){
        System.out.println(salesRequestDTO.getCustomerName() + " "+ salesRequestDTO.getTotalAmountSummary() + " "+ salesRequestDTO.getSaleItems()  + " "+ salesRequestDTO.getQuantitySoldSummary());
        System.out.println(salesRequestDTO.toString());
        return new ResponseEntity<>(salesRequestDTO, HttpStatus.CREATED);
    }
}
