package com.example.umerautos.controllers;

import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.services.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProductController {

    private final ProductsService productsService;



    @GetMapping("/products")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit) {

        var response = productsService.findAll(name, page, limit);

        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }



    @GetMapping("/products/{productId}")
    public ResponseEntity<?>  findAll(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductsResponseDTO productsResponseDTO = productsService.findById(productId);

        if (productsResponseDTO.getId() != null){
            return new ResponseEntity<>(productsResponseDTO, HttpStatus.OK);
        }
        else {
            throw  new ResourceNotFoundException();

        }
    }

    @PostMapping("/products")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<?> save(@Valid  @RequestBody ProductsRequestDTO productsRequestDTO){
        ProductsResponseDTO productsResponseDTO = productsService.createOne(productsRequestDTO);
        if (productsResponseDTO.getId() != null){
            return new ResponseEntity<>(productsResponseDTO, HttpStatus.CREATED);

        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/products/{productId}")
    @CacheEvict(value = "products", key = "#id")
    public ResponseEntity<?> deleteOne(@PathVariable UUID productId){

        try {
            productsService.deleteOne(productId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch (Exception e){
            throw new RuntimeException();
        }

    }

    @PutMapping("/products/{productId}")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<?> updateProduct(@PathVariable UUID productId, @Valid @RequestBody ProductsRequestDTO requestDTO){

        ProductsResponseDTO productsResponseDTO = productsService.updateOne(productId, requestDTO);

        if (productsResponseDTO.getId() != null){
            return new ResponseEntity<>(productsResponseDTO, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
