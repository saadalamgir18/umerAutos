package com.example.umerautos.controllers;


import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.entities.Brands;
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
    public ResponseEntity<Object> findAll(){

        List<BrandsResponseDTO> brandsResponseDTOS =   brandsService.findAll();
        if (!brandsResponseDTOS.isEmpty()){
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", brandsResponseDTOS);
        }
        else {
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong", null);

        }

    }
    @GetMapping("/brands/{brandId}")
    public ResponseEntity<Object> findAll(@PathVariable UUID brandId){

        BrandsResponseDTO brandsResponseDTOS =   brandsService.findOne(brandId);
        if (brandsResponseDTOS != null){
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", brandsResponseDTOS);
        }
        else {
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong", null);

        }

    }

    @PostMapping("/brands")
    public ResponseEntity<Object> saveOne(@Valid @RequestBody Brands brands){
        BrandsResponseDTO brandsResponseDTOS =   brandsService.createOne(brands);
        if (brandsResponseDTOS.getId() != null){
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", brandsResponseDTOS);
        }
        else {
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong", null);

        }
    }
}
