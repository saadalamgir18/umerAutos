package com.example.umerautos.controllers;


import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.services.BrandsService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1")
public class BrandsController {
    private final BrandsService brandsService;
    private static final Logger logger = LoggerFactory.getLogger(BrandsController.class);

    public BrandsController(BrandsService brandsService) {
        this.brandsService = brandsService;
    }


    @GetMapping("/brands")
    public ResponseEntity<List<BrandsResponseDTO>> findAll() {
        logger.info("Request received to fetch all brands");
        try {
            List<BrandsResponseDTO> brandsResponseDTOS = brandsService.findAll();
            logger.info("Successfully fetched {} brands", brandsResponseDTOS.size());
            return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error occurred while fetching all brands", e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/brands/{brandId}")
    public ResponseEntity<BrandsResponseDTO> findOne(@PathVariable Long brandId) {
        logger.info("Request received to fetch brand with ID: {}", brandId);
        try {
            BrandsResponseDTO brandsResponseDTOS = brandsService.findOne(brandId);
            if (brandsResponseDTOS != null) {
                logger.info("Successfully fetched brand with ID: {}", brandId);
                return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.OK);
            } else {
                logger.warn("Brand not found with ID: {}", brandId);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while fetching brand with ID: {}", brandId, e);
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/brands")
    public ResponseEntity<BrandsResponseDTO> saveOne(@Valid @RequestBody BrandsRequestDTO brands) {
        logger.info("Request received to create a new brand: {}", brands.name());
        try {
            BrandsResponseDTO brandsResponseDTOS = brandsService.createOne(brands);
            if (brandsResponseDTOS != null) {
                logger.info("Successfully created brand with ID: {}", brandsResponseDTOS.id());
                return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.CREATED);
            } else {
                logger.error("Failed to create brand: {}", brands.name());
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error occurred while creating brand: {}", brands.name(), e);
            throw e;
        }
    }

    @PutMapping("/brands/{brandId}")
    public ResponseEntity<BrandsResponseDTO> updateOne(@Valid @RequestBody BrandsRequestDTO brands, @PathVariable Long brandId) {
        logger.info("Request received to update brand with ID: {}", brandId);
        try {
            BrandsResponseDTO brandsResponseDTOS = brandsService.updateOne(brands, brandId);
            if (brandsResponseDTOS != null) {
                logger.info("Successfully updated brand with ID: {}", brandId);
                return new ResponseEntity<>(brandsResponseDTOS, HttpStatus.OK);
            } else {
                logger.warn("Failed to update brand. Brand not found with ID: {}", brandId);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error occurred while updating brand with ID: {}", brandId, e);
            throw new RuntimeException(e);
        }
    }
}
