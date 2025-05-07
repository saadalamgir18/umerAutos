package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.SalesResponseDTO;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.services.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class SalesController {

    @Autowired private SalesService salesService;

    @GetMapping("/today-sales")
    public ResponseEntity<?> findAll() {


        return salesService.findAll().isEmpty() ? CustomResponse.generateResponse(HttpStatus.OK, true , "successful", new ArrayList<>())
                :
             CustomResponse.generateResponse(HttpStatus.OK, true, "successful", salesService.findAll());


        }

    @GetMapping("/today-sale/totalSale")
    public ResponseEntity<?> todayTotalSale() {


        return salesService.getTodayTotalSalesAmount() > 0 ? CustomResponse.generateResponse(HttpStatus.OK, true , "successful",salesService.getTodayTotalSalesAmount())
                :
                CustomResponse.generateResponse(HttpStatus.OK, true, "successful", 0);


    }





}




