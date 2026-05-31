package com.example.umerautos.controllers;

import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;
import com.example.umerautos.services.SuppliersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
public class SuppliersController {
    private SuppliersService suppliersService;
    private static final Logger logger = LoggerFactory.getLogger(SuppliersController.class);

    public SuppliersController(SuppliersService suppliersService) {
        this.suppliersService = suppliersService;
    }

    @GetMapping("/suppliers")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit
    ) {
        logger.info("Request received to fetch all suppliers. Page: {}, Limit: {}", page, limit);
        try {
            var suppliersResponseDTOS = suppliersService.findAll(page, limit);
            logger.info("Successfully fetched suppliers");
            return new ResponseEntity<>(suppliersResponseDTOS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching suppliers", e);
            throw new RuntimeException();
        }
    }

    @PostMapping("/suppliers")
    public ResponseEntity<?> saveOne(@RequestBody SuppliersRequestDTO requestDTO) {
        logger.info("Request received to save supplier: {}", requestDTO.company());
        try {
            SuppliersResponseDTO suppliersResponseDTO = suppliersService.saveOne(requestDTO);
            logger.info("Successfully saved supplier with ID: {}", suppliersResponseDTO.email());
            return new ResponseEntity<>(suppliersResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error saving supplier: {}", requestDTO.company(), e);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        logger.info("Request received to delete supplier with ID: {}", id);
        try {
            suppliersService.deleteOne(id);
            logger.info("Successfully deleted supplier with ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting supplier with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }
}
