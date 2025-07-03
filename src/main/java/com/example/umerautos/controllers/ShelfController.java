package com.example.umerautos.controllers;

import com.example.umerautos.dto.ShelfResponseDTO;
import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.globalException.RunTimeException;
import com.example.umerautos.services.ShelfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ShelfController {

    private final ShelfService shelfService;

    @PostMapping("/shelf")
    public ResponseEntity<?> saveOne(@RequestBody ShelfCode shelfCode) {
        ShelfResponseDTO shelfResponseDTO = shelfService.createOne(shelfCode);
        if (shelfResponseDTO.id() != null) {

            return new ResponseEntity<>(shelfResponseDTO, HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/shelf")
    public ResponseEntity<?> findAll() {

        try {

            List<ShelfResponseDTO> shelfResponseDTOS = shelfService.findAll();

            return new ResponseEntity<>(shelfResponseDTOS, HttpStatus.OK);
        } catch (Exception e) {
            throw new RunTimeException();

        }


    }

}

