package com.example.umerautos.controllers;

import com.example.umerautos.dto.SaleUpdateRequestDTO;
import com.example.umerautos.dto.SalesUpdateResponseDTO;
import com.example.umerautos.services.SalesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class SalesController {

    private SalesService salesService;

    @GetMapping("/today-sales")
    public ResponseEntity<?> findTodaySale() {


        try {
            return new ResponseEntity<>(salesService.findTodaySales(), HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/sales")
    public ResponseEntity<?> findAll(@RequestParam int page, @RequestParam int limit) {

        var response = salesService.findAll(page, limit);

        return new ResponseEntity<>(response, HttpStatus.OK);


    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable UUID id) {
        try {



            SalesUpdateResponseDTO salesUpdateResponseDTO = salesService.findSaleById(id);

            return new ResponseEntity<>(salesUpdateResponseDTO, HttpStatus.OK);


        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    @PutMapping("/sales/{id}")
    public ResponseEntity<?> updateSale(@PathVariable UUID id, @Valid @RequestBody SaleUpdateRequestDTO requestDTO) {

        try {

            SalesUpdateResponseDTO responseDTO = salesService.updateSale(requestDTO, id);
            if (requestDTO != null) {
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable UUID id) {
        try {

            salesService.deleteOne(id);
            return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/today-sale/totalSale")
    public ResponseEntity<?> todayTotalSale() {

        try {
            double todayTotalSalesAmount = salesService.getTodayTotalSalesAmount();

            return new ResponseEntity<>(todayTotalSalesAmount, HttpStatus.OK);
        }catch (Exception e){

            throw new RuntimeException(e);

        }
    }

    @GetMapping("/sales/monthly-revenue")
    public ResponseEntity<?> getMonthlyRevenue() {
        try {

            double monthlyRevenue = salesService.getMonthlyRevenue();

            return new ResponseEntity<>(monthlyRevenue, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}




