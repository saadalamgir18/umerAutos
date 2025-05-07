package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.entities.Products;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.services.ProductsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> findAll(@RequestParam(required = false) String name) throws ResourceNotFoundException {
        List<ProductsResponseDTO> productsResponseDTO = productsService.findAll(name);
        if (!productsResponseDTO.isEmpty()){
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", productsResponseDTO);
        }
        else {
            throw  new ResourceNotFoundException();

        }

    }
    @GetMapping("/products/{productId}")
    public ResponseEntity<?>  findAll(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductsResponseDTO productsResponseDTO = productsService.findById(productId);
        if (productsResponseDTO.getId() != null){
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", productsResponseDTO);
        }
        else {
            throw  new ResourceNotFoundException();

        }
    }

    @PostMapping("/products")
    public ResponseEntity<Object> save(@RequestBody ProductsRequestDTO productsRequestDTO){
        ProductsResponseDTO productsResponseDTO = productsService.createOne(productsRequestDTO);
        if (productsResponseDTO.getId() != null){
            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", productsResponseDTO);
        }
        else {
            System.out.println("somethig went wrong");
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong while creating product ", null);

        }

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<?> deleteOne(@PathVariable UUID productId){

        return productsService.deleteOne(productId);

    }

//    @GetMapping("/products/low-stock")
//    public ResponseEntity<?> lowStock(){
//
//        return null;
//
//    }
}
