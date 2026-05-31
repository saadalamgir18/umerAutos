package com.example.umerautos.controllers;

import com.example.umerautos.dto.ShelfResponseDTO;
import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.globalException.RunTimeException;
import com.example.umerautos.services.ShelfService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShelfController {

    private final ShelfService shelfService;
    private static final Logger logger = LoggerFactory.getLogger(ShelfController.class);

    @PostMapping("/shelf")
    public ResponseEntity<?> saveOne(@RequestBody ShelfCode shelfCode) {
        logger.info("Request received to create shelf code: {}", shelfCode.getName());
        try {
            ShelfResponseDTO shelfResponseDTO = shelfService.createOne(shelfCode);
            if (shelfResponseDTO.id() != null) {
                logger.info("Successfully created shelf code with ID: {}", shelfResponseDTO.id());
                return new ResponseEntity<>(shelfResponseDTO, HttpStatus.CREATED);
            } else {
                logger.error("Failed to create shelf code: {}", shelfCode.getName());
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error creating shelf code: {}", shelfCode.getName(), e);
            throw e;
        }
    }

    @GetMapping("/shelf")
    public ResponseEntity<?> findAll() {
        logger.info("Request received to fetch all shelf codes");
        try {
            List<ShelfResponseDTO> shelfResponseDTOS = shelfService.findAll();
            logger.info("Successfully fetched {} shelf codes", shelfResponseDTOS.size());
            return new ResponseEntity<>(shelfResponseDTOS, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching shelf codes", e);
            throw new RunTimeException();
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/shelf/{id}")
    public ResponseEntity<?> updateOne(@PathVariable Long id, @RequestBody ShelfCode shelfCode) {
        logger.info("Request received to update shelf code with ID: {}", id);
        try {
            ShelfResponseDTO shelfResponseDTO = shelfService.updateOne(id, shelfCode);
            if (shelfResponseDTO != null) {
                logger.info("Successfully updated shelf code with ID: {}", id);
                return new ResponseEntity<>(shelfResponseDTO, HttpStatus.OK);
            } else {
                logger.warn("Failed to update shelf code. ID not found: {}", id);
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error updating shelf code with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/shelf/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable Long id) {
        logger.info("Request received to delete shelf code with ID: {}", id);
        try {
            shelfService.deleteOne(id);
            logger.info("Successfully deleted shelf code with ID: {}", id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting shelf code with ID: {}", id, e);
            throw new RuntimeException(e);
        }
    }

}
