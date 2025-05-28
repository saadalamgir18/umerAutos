package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.services.SalesSummaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SalesSummaryController {

    private SalesSummaryService salesSummaryService;

    @PostMapping("/sales-summary")
    public ResponseEntity<?> saveSales(@Valid  @RequestBody SalesSummaryRequestDTO salesRequestDTO){
       SalesSummaryResponseDTO salesSummaryResponseDTO = salesSummaryService.saveOne(salesRequestDTO);
       if (salesSummaryResponseDTO.getId() != null){
           return CustomResponse.generateResponse(HttpStatus.CREATED, true, "sales created successfully!", salesSummaryResponseDTO);
       }else {
           return CustomResponse.generateResponse(HttpStatus.BAD_REQUEST, false, "arguments are not correct", null);
       }
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<?> getAll(){
        List<SalesSummaryResponseDTO> salesSummaryResponseDTOS = salesSummaryService.findAll();
        if (!salesSummaryResponseDTOS.isEmpty()){
            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "successful", salesSummaryResponseDTOS);
        }else {
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong", null);
        }
    }
}
