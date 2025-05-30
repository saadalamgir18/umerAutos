package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.services.SalesSummaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class SalesSummaryController {

    @Autowired private SalesSummaryService salesSummaryService;

    @PostMapping("/sales-summary")
    public ResponseEntity<?> saveSales(@Valid  @RequestBody SalesSummaryRequestDTO salesRequestDTO){
       SalesSummaryResponseDTO salesSummaryResponseDTO = salesSummaryService.saveOne(salesRequestDTO);
       if (salesSummaryResponseDTO.getId() != null){
           return new ResponseEntity<>(salesSummaryResponseDTO, HttpStatus.CREATED);
       }else {

           return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
       }
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit,
            @RequestParam(required = false, defaultValue = "paid") String status
    ){
        PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());

        var response  = salesSummaryService.finadSalesSummary(page, limit, paymentStatus);
        System.out.println(response);
        if (response.getData() != null){
            return new ResponseEntity<>(response, HttpStatus.OK);

        }else {

            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
