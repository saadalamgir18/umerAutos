package com.example.umerautos.controllers;

import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;
import com.example.umerautos.services.CompatibleModelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class CompatibleModelsController {

    private final CompatibleModelService modelService;

    public CompatibleModelsController(CompatibleModelService modelService) {
        this.modelService = modelService;
    }
    
    @PostMapping("/compatible-models")
    public ResponseEntity<?> createOne(@Valid @RequestBody CompatibleModelRequestDTO requestDTO) {
        CompatibleModelResponseDTO modelResponseDTO = modelService.createOne(requestDTO);
        if (modelResponseDTO.id() != null) {
            return new ResponseEntity<>(modelResponseDTO, HttpStatus.CREATED);

        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/compatible-models")
    public ResponseEntity<?> findAll() {
        Set<CompatibleModelResponseDTO> responseDTO = modelService.findAll();
        if (!responseDTO.isEmpty()) {

            return new ResponseEntity<>(responseDTO, HttpStatus.OK);

        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @GetMapping("/compatible-models/{modelId}")
    public ResponseEntity<?> findOne(@PathVariable UUID modelId) {
        try {

            CompatibleModelResponseDTO responseDTO = modelService.findOne(modelId);
            if (responseDTO != null) {
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);


            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PutMapping("/compatible-models/{modelId}")
    public ResponseEntity<?> updateOne(@Valid @RequestBody CompatibleModelRequestDTO requestDTO, @PathVariable UUID modelId) {
        try {

            CompatibleModelResponseDTO responseDTO = modelService.updateOne(requestDTO, modelId);
            if (responseDTO != null) {
                return new ResponseEntity<>(responseDTO, HttpStatus.OK);

            }
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
