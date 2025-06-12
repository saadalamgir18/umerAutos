package com.example.umerautos.controllers;

import com.example.umerautos.configuration.AppConstants;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.dto.SalesSummaryUpdate;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.services.SalesSummaryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
public class SalesSummaryController {

    @Autowired
    private SalesSummaryService salesSummaryService;

    @PostMapping("/sales-summary")
    public ResponseEntity<?> saveSales(@Valid @RequestBody SalesSummaryRequestDTO salesRequestDTO) {
        SalesSummaryResponseDTO salesSummaryResponseDTO = salesSummaryService.saveOne(salesRequestDTO);
        if (salesSummaryResponseDTO.id() != null) {
            return new ResponseEntity<>(salesSummaryResponseDTO, HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE) int limit,
            @RequestParam(required = false, defaultValue = "paid") String status
    ) {
        PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());

        var response = salesSummaryService.findSalesSummary(page, limit, paymentStatus);
        System.out.println(response);
        if (response.getData() != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/sales-summary/{id}")
    public ResponseEntity<?> getOne(@PathVariable UUID id) {

        SalesSummaryResponseDTO summaryResponseDTO = salesSummaryService.findSalesSummaryById(id);
        System.out.println(summaryResponseDTO);
        if (summaryResponseDTO != null) {
            return new ResponseEntity<>(summaryResponseDTO, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }
    }

    @PutMapping("/sales-summary/{id}")
    public ResponseEntity<?> updateOne(@RequestBody SalesSummaryUpdate request, @PathVariable UUID id) {
        SalesSummaryResponseDTO summaryResponseDTO = salesSummaryService.updateSaleSummaryById(id, request);

        if (summaryResponseDTO != null) {
            return new ResponseEntity<>(summaryResponseDTO, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

    }

    @PatchMapping("/sales-summary/{id}")
    public ResponseEntity<?> patchOne(@PathVariable UUID id) {
        SalesSummaryResponseDTO summaryResponseDTO = salesSummaryService.updateSaleSummaryById(id);

        if (summaryResponseDTO != null) {
            return new ResponseEntity<>(summaryResponseDTO, HttpStatus.OK);

        } else {

            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        }

    }

}
