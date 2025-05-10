package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;
import com.example.umerautos.services.CompatibleModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/v1")
public class CompatibleModelsController {
    @Autowired
    private CompatibleModelService modelService;

    @PostMapping("/compatible-models")
    public ResponseEntity<?> createOne(@RequestBody CompatibleModelRequestDTO requestDTO){
        CompatibleModelResponseDTO modelResponseDTO = modelService.createOne(requestDTO);
        if (modelResponseDTO.getId() != null){
            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", modelResponseDTO);

        }
        return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "fail", null);
    }

    @GetMapping("/compatible-models")
    public ResponseEntity<?> findAll(){
        Set<CompatibleModelResponseDTO> responseDTO = modelService.findAll();
        if (!responseDTO.isEmpty()){
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", responseDTO);

        }
        return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "fail", null);

    }
}
