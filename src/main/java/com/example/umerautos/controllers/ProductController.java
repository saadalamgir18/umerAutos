package com.example.umerautos.controllers;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.services.ProductsService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private final ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "6") int limit) {

        var response = productsService.findAll(name, page, limit);

        return CustomResponse.generatePaginationResponse(
                HttpStatus.OK, true, "success",
                response.getData(), response.getPagination()
        );
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
    public ResponseEntity<?> save(@Valid  @RequestBody ProductsRequestDTO productsRequestDTO){
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

        try {
            productsService.deleteOne(productId);
            return CustomResponse.generateResponse(HttpStatus.OK, true, "success", null);
        }catch (Exception e){
            throw new RuntimeException();
        }

    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<?> updateProduct(@PathVariable UUID productId, @Valid @RequestBody ProductsRequestDTO requestDTO){

        System.out.println(productId);
        System.out.println(requestDTO);
        System.out.println(requestDTO.getCompatibleModelIds());
        ProductsResponseDTO productsResponseDTO = productsService.updateOne(productId, requestDTO);

        if (productsResponseDTO.getId() != null){
            return CustomResponse.generateResponse(HttpStatus.CREATED, true, "success", productsResponseDTO);
        }
        else {
            System.out.println("somethig went wrong");
            return CustomResponse.generateResponse(HttpStatus.INTERNAL_SERVER_ERROR, false, "something went wrong while creating product ", null);

        }

    }


}
