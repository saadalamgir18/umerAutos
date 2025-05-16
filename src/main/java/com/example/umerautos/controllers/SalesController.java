package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.SaleUpdateRequestDTO;
import com.example.umerautos.dto.SalesUpdateResponseDTO;
import com.example.umerautos.services.SalesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1")
public class SalesController {

    @Autowired private SalesService salesService;

    @GetMapping("/today-sales")
    public ResponseEntity<?> findTodaySale() {


        try {

            return CustomResponse.generateResponse(HttpStatus.OK, true, "successful", salesService.findTodaySales());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        }
    @GetMapping("/sales")
    public ResponseEntity<?> findAll(@RequestParam int page, @RequestParam int limit) {

        var response = salesService.findAll(page, limit);

        return  CustomResponse.generatePaginationResponse(HttpStatus.OK, true, "successful", response.getData(), response.getPagination());


    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable UUID id){
        try {

            SalesUpdateResponseDTO salesUpdateResponseDTO = salesService.findSaleById(id);
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", salesUpdateResponseDTO);

        } catch (Exception e) {
            System.out.println("runtime exception");
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "fail", null);
        }

    }

    @PutMapping("/sales/{id}")
    public ResponseEntity<?> updateSale(@PathVariable UUID id, @Valid @RequestBody SaleUpdateRequestDTO requestDTO){

        try {

            SalesUpdateResponseDTO responseDTO = salesService.updateSale(requestDTO, id);
            if (requestDTO != null){
                return CustomResponse.generateResponse(HttpStatus.OK, true, "success", responseDTO);

            }
            return CustomResponse.generateResponse(HttpStatus.NOT_FOUND, false, "fail", null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/sales/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable UUID id){
        try {

            salesService.deleteOne(id);
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/today-sale/totalSale")
    public ResponseEntity<?> todayTotalSale() {


        return salesService.getTodayTotalSalesAmount() > 0 ? CustomResponse.generateResponse(HttpStatus.OK, true , "successful",salesService.getTodayTotalSalesAmount())
                :
                CustomResponse.generateResponse(HttpStatus.OK, true, "successful", 0);

    }

    @GetMapping("/sales/monthly-revenue")
    public ResponseEntity<?> getMonthlyRevenue(){
        try {

            double monthlyRevenue = salesService.getMonthlyRevenue();
            return CustomResponse.generateResponse(HttpStatus.OK, true, "successful", monthlyRevenue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }






}




