package com.example.umerautos.controllers;

import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;
import com.example.umerautos.services.CompatibleModelService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class CompatibleModelsController {

    private final CompatibleModelService modelService;
    private static final Logger logger = LoggerFactory.getLogger(CompatibleModelsController.class);

    public CompatibleModelsController(CompatibleModelService modelService) {
        this.modelService = modelService;
    }

    @PostMapping("/compatible-models")
    public ResponseEntity<?> createOne(@Valid @RequestBody CompatibleModelRequestDTO requestDTO) {
        logger.info("Request received to create compatible model: {}", requestDTO.name());
        try {
            CompatibleModelResponseDTO modelResponseDTO = modelService.createOne(requestDTO);
            if (modelResponseDTO.id() != null) {
                logger.info("Successfully created compatible model with ID: {}", modelResponseDTO.id());
                return new ResponseEntity<>(modelResponseDTO, HttpStatus.CREATED);
            }
            logger.error("Failed to create compatible model: {}", requestDTO.name());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            logger.error("Error creating compatible model: {}", requestDTO.name(), e);
            throw e;
        }
    }

    @GetMapping("/compatible-models")
    public ResponseEntity<?> findAll() {
        logger.info("Request received to fetch all compatible models");
        try {
            Set<CompatibleModelResponseDTO> responseDTO = modelService.findAll();
            logger.info("Successfully fetched {} compatible models", responseDTO.size());
            return new ResponseEntity<>(responseDTO, HttpStatus.OK);
        } catch (RuntimeException e) {
            logger.error("Error fetching all compatible models", e);
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/compatible-models/{modelId}")
    public ResponseEntity<?> findOne(@PathVariable Long modelId) {
        logger.info("Request received to fetch compatible model with ID: {}", modelId);
        try {
            CompatibleModelResponseDTO responseDTO = modelService.findOne(modelId);
            if (responseDTO != null) {
                logger.info("Successfully fetched compatible model with ID: {}", modelId);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            logger.warn("Compatible model not found with ID: {}", modelId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error fetching compatible model with ID: {}", modelId, e);
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/compatible-models/{modelId}")
    public ResponseEntity<?> updateOne(@Valid @RequestBody CompatibleModelRequestDTO requestDTO, @PathVariable Long modelId) {
        logger.info("Request received to update compatible model with ID: {}", modelId);
        try {
            CompatibleModelResponseDTO responseDTO = modelService.updateOne(requestDTO, modelId);
            if (responseDTO != null) {
                logger.info("Successfully updated compatible model with ID: {}", modelId);
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);
            }
            logger.warn("Failed to update compatible model. ID not found: {}", modelId);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating compatible model with ID: {}", modelId, e);
            throw new RuntimeException(e);
        }
    }
}
