package com.example.umerautos.controllers;

import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;
import com.example.umerautos.services.SuppliersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class SuppliersController {
    private SuppliersService suppliersService;

    public SuppliersController(SuppliersService suppliersService) {
        this.suppliersService = suppliersService;
    }

    @GetMapping("/suppliers")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit
    ) {
        try {
            var suppliersResponseDTOS = suppliersService.findAll(page, limit);

            return new ResponseEntity<>(suppliersResponseDTOS, HttpStatus.OK);

        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    @PostMapping("/suppliers")
    public ResponseEntity<?> saveOne(@RequestBody SuppliersRequestDTO requestDTO) {
        try {
            SuppliersResponseDTO suppliersResponseDTO = suppliersService.saveOne(requestDTO);
            return new ResponseEntity<>(suppliersResponseDTO, HttpStatus.CREATED);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable UUID id) {
        try {
            suppliersService.deleteOne(id);

            return new ResponseEntity<>(null, HttpStatus.OK);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
