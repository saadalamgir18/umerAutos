package com.example.umerautos.controllers;

import com.example.umerautos.configuration.AppConstants;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.dto.UpdateDebtorsSales;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.services.SalesSummaryService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class SalesSummaryController {
    Logger logger = LoggerFactory.getLogger(SalesSummaryController.class);

    @Autowired
    private SalesSummaryService salesSummaryService;

    @PostMapping("/sales-summary")
    public ResponseEntity<?> saveSales(@Valid @RequestBody SalesSummaryRequestDTO salesRequestDTO) {
        logger.info("Request received to save sales summary");
        try {
            SalesSummaryResponseDTO salesSummaryResponseDTO = salesSummaryService.saveOne(salesRequestDTO);
            if (salesSummaryResponseDTO.id() != null) {
                logger.info("Sales summary saved successfully with ID: {}", salesSummaryResponseDTO.id());
                return new ResponseEntity<>(salesSummaryResponseDTO, HttpStatus.CREATED);
            } else {
                logger.error("Failed to save sales summary");
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            logger.error("Exception occurred while saving sales summary: ", e);
            throw e;
        }
    }

    @GetMapping("/sales-summary")
    public ResponseEntity<?> getAll(
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE) int limit,
            @RequestParam(required = false, defaultValue = "0") int customerId,
            @RequestParam(required = false, defaultValue = "unpaid") String status

    ) {
        logger.info("Request received to get all sales summaries. Page: {}, Limit: {}, CustomerId: {}, Status: {}", page, limit, customerId, status);
        try {
            PaymentStatus paymentStatus = PaymentStatus.valueOf(status.toUpperCase());

            var response = salesSummaryService.findSalesSummary(page, limit, customerId, paymentStatus);
            if (response.getData() != null) {
                logger.info("Sales summaries retrieved successfully. Total items: {}", response.getPagination().totalItems());
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                logger.error("Failed to retrieve sales summaries");
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

            }
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving sales summaries: ", e);
            throw e;
        }
    }

    @GetMapping("/sales-summary/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        logger.info("Request received to get sales summary with ID: {}", id);
        try {
            SalesSummaryResponseDTO summaryResponseDTO = salesSummaryService.findSalesSummaryById(id);
            if (summaryResponseDTO != null) {
                logger.info("Sales summary retrieved successfully for ID: {}", id);
                return new ResponseEntity<>(summaryResponseDTO, HttpStatus.OK);

            } else {
                logger.error("Sales summary not found for ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            logger.error("Exception occurred while retrieving sales summary with ID: " + id, e);
            throw e;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/sales-summary/{id}")
    public ResponseEntity<?> updateOne(@PathVariable Long id) {
        logger.info("Request received to update sales summary with ID: {}", id);
        try {
            String summaryResponseDTO = salesSummaryService.updateSaleById(id);

            if (summaryResponseDTO != null) {
                logger.info("Sales summary updated successfully for ID: {}", id);
                return new ResponseEntity<>(summaryResponseDTO, HttpStatus.OK);

            } else {
                logger.error("Failed to update sales summary for ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            logger.error("Exception occurred while updating sales summary with ID: " + id, e);
            throw e;
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/sales-summary/{id}")
    public ResponseEntity<?> patchOne(@PathVariable Long id, @RequestBody UpdateDebtorsSales request) {
        logger.info("Request received to patch sales summary with ID: {}", id);
        try {
            SalesSummaryResponseDTO summaryResponseDTO = salesSummaryService.updateSaleSummaryById(id, request);

            if (summaryResponseDTO != null) {
                logger.info("Sales summary patched successfully for ID: {}", id);
                return new ResponseEntity<>(summaryResponseDTO, HttpStatus.OK);

            } else {
                logger.error("Failed to patch sales summary for ID: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            logger.error("Exception occurred while patching sales summary with ID: " + id, e);
            throw e;
        }

    }

}
