package com.example.umerautos.controllers;


import com.example.umerautos.customresponse.CustomResponse;
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
    public ResponseEntity<Object> findOne(@PathVariable UUID brandId){

        try {
            BrandsResponseDTO brandsResponseDTOS =   brandsService.findOne(brandId);
            if (brandsResponseDTOS != null){
                return CustomResponse.generateResponse(HttpStatus.OK, true, "success", brandsResponseDTOS);
            }
            else {
                return CustomResponse.generateResponse(HttpStatus.NOT_FOUND, false, "brand is not available for the id: " + brandId, null);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/brands")
    public ResponseEntity<Object> saveOne(@Valid @RequestBody BrandsRequestDTO brands){
        BrandsResponseDTO brandsResponseDTOS =   brandsService.createOne(brands);
        if (brandsResponseDTOS != null){
            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", brandsResponseDTOS);
        }
        else {
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong", null);

        }
    }

    @PutMapping("/brands/{brandId}")
    public ResponseEntity<Object> updateOne(@Valid @RequestBody BrandsRequestDTO brands, @PathVariable UUID brandId){
        try {

            BrandsResponseDTO brandsResponseDTOS =   brandsService.updateOne(brands, brandId);
            if (brandsResponseDTOS != null){
                return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", brandsResponseDTOS);
            }
            else {
                return CustomResponse.generateResponse(HttpStatus.NOT_FOUND, false, "Brand with this id is not present: " + brandId, null);

            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
