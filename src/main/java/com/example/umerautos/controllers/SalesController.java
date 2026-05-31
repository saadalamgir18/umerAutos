package com.example.umerautos.controllers;

import com.example.umerautos.configuration.AppConstants;
import com.example.umerautos.dto.SaleUpdateRequestDTO;
import com.example.umerautos.dto.SalesUpdateResponseDTO;
import com.example.umerautos.services.SalesService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class SalesController {

    private SalesService salesService;
    private static final Logger logger = LoggerFactory.getLogger(SalesController.class);


    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/today-sales")
    public ResponseEntity<?> findTodaySale(
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE) int limit
    ) {
        logger.info("Request received to fetch today's sales. Page: {}, Limit: {}", page, limit);
        try {
            return new ResponseEntity<>(salesService.findTodaySales(page, limit), HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching today's sales", e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/sales")
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = AppConstants.PAGE_NUMBER) int page, @RequestParam(defaultValue = AppConstants.PAGE_SIZE) int limit) {
        logger.info("Request received to fetch all sales. Page: {}, Limit: {}", page, limit);
        try {
            var response = salesService.findAll(page, limit);
            logger.info("Successfully fetched all sales");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching all sales", e);
            throw e;
        }
    }

    @GetMapping("/sales/{id}")
    public ResponseEntity<?> getSaleById(@PathVariable Long id) {
        logger.info("Request received to fetch sale with ID: {}", id);
        try {
            SalesUpdateResponseDTO salesUpdateResponseDTO = salesService.findSaleById(id);

            if (salesUpdateResponseDTO.id() == null) {
                logger.warn("Sale not found with ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            logger.info("Successfully fetched sale with ID: {}", id);
            return new ResponseEntity<>(salesUpdateResponseDTO, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching sale with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/sales/{id}")
    public ResponseEntity<?> updateSale(@PathVariable Long id, @Valid @RequestBody SaleUpdateRequestDTO requestDTO) {
        logger.info("Request received to update sale with ID: {}", id);
        try {
            SalesUpdateResponseDTO responseDTO = salesService.updateSale(requestDTO, id);
            if (responseDTO != null) {
                logger.info("Successfully updated sale with ID: {}", id);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            logger.warn("Failed to update sale. ID not found: {}", id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating sale with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/sales/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        logger.info("Request received to delete sale with ID: {}", id);
        try {
            salesService.deleteOne(id);
            logger.info("Successfully deleted sale with ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting sale with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/today-sale/totalSale")
    public ResponseEntity<?> todayTotalSale() {
        logger.info("Request received to fetch today's total sales amount");
        try {
            int todayTotalSalesAmount = salesService.getTodayTotalSalesAmount();
            logger.info("Successfully fetched today's total sales amount: {}", todayTotalSalesAmount);
            return new ResponseEntity<>(todayTotalSalesAmount, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching today's total sales amount", e);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/sales/monthly-revenue")
    public ResponseEntity<?> getMonthlyRevenue() {
        logger.info("Request received to fetch monthly revenue");
        try {
            int monthlyRevenue = salesService.getMonthlyRevenue();
            logger.info("Successfully fetched monthly revenue: {}", monthlyRevenue);
            return new ResponseEntity<>(monthlyRevenue, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching monthly revenue", e);
            throw new RuntimeException(e);
        }
    }
}
