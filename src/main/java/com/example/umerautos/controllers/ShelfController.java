package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.ShelfResponseDTO;
import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.services.ShelfService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ShelfController {

    private final ShelfService shelfService;

    public ShelfController(ShelfService shelfService) {
        this.shelfService = shelfService;
    }

    @PostMapping("/shelf")
    public ResponseEntity<?> saveOne(@RequestBody ShelfCode shelfCode){
        ShelfResponseDTO shelfResponseDTO = shelfService.createOne(shelfCode);
        if (shelfResponseDTO.getId() != null){
            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", shelfResponseDTO);
        }
        else {
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong while creating product ", null);

        }
    }

    @GetMapping("/shelf")
    public ResponseEntity<?> findAll(){
        List<ShelfResponseDTO> shelfResponseDTOS =  shelfService.findAll();
        if (!shelfResponseDTOS.isEmpty()){
            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", shelfResponseDTOS);
        }
        else {
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong while creating product ", null);

        }

    }

}
