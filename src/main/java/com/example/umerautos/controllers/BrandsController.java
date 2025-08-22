package com.example.umerautos.controllers;


import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.services.BrandsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1")
public class BrandsController {
    private final BrandsService brandsService;

    public BrandsController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }


    @GetMapping("/brands")
    public ResponseEntity<List<BrandsResponseDTO>> findAll() {

        try {
            List<BrandsResponseDTO> brandsResponseDTOS = brandsService.findAll();
            return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.OK);


        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<BrandsResponseDTO> findOne(@PathVariable UUID brandId) {

        try {
            BrandsResponseDTO brandsResponseDTOS = brandsService.findOne(brandId);
            if (brandsResponseDTOS != null) {
                return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandsResponseDTO> saveOne(@Valid @RequestBody BrandsRequestDTO brands) {
        BrandsResponseDTO brandsResponseDTOS = brandsService.createOne(brands);
        if (brandsResponseDTOS != null) {
            return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @PutMapping("/brands/{brandId}")
    public ResponseEntity<BrandsResponseDTO> updateOne(@Valid @RequestBody BrandsRequestDTO brands, @PathVariable UUID brandId) {
        try {

            BrandsResponseDTO brandsResponseDTOS = brandsService.updateOne(brands, brandId);
            if (brandsResponseDTOS != null) {
                return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.OK);

            } else {
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
