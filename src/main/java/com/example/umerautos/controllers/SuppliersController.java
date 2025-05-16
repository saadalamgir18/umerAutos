package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;
import com.example.umerautos.services.SuppliersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class SuppliersController {
    @Autowired private SuppliersService suppliersService;

    @GetMapping("/suppliers")
    public ResponseEntity<?> findAll(){
        try {
            List<SuppliersResponseDTO> suppliersResponseDTOS = suppliersService.findAll();

            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", suppliersResponseDTOS);

        }catch (Exception e){
             throw new RuntimeException();
        }

    }

    @PostMapping("/suppliers")
    public ResponseEntity<?> saveOne(@RequestBody SuppliersRequestDTO requestDTO){
        try {
            SuppliersResponseDTO suppliersRequestDTO = suppliersService.saveOne(requestDTO);
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", suppliersRequestDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @DeleteMapping("/suppliers/{id}")
    public ResponseEntity<?> deleteOne(@PathVariable UUID id){
        try {
            suppliersService.deleteOne(id);

            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", null);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
