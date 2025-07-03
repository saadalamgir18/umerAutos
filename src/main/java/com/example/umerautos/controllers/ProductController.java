package com.example.umerautos.controllers;

import com.example.umerautos.configuration.AppConstants;
import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.services.ProductsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(description = "These end points has rest controller for products", name = "Product Controller")
public class ProductController {

    private final ProductsService productsService;


    @Operation(operationId = "getAllProducts",
            description = "This end point is use to get all products",
            summary = "getting all products",
            parameters = {
                    @Parameter(name = "name", required = false),
                    @Parameter(name = "page", description = "enter page number", required = false),
                    @Parameter(name = "limit", description = "enter page size", required = false)
            }

    )
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "returns products list",

                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductsResponseDTO[].class))
                    )
            }
    )
    @GetMapping("/products")
    public ResponseEntity<?> findAll(
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_NUMBER) int page,
            @RequestParam(required = false, defaultValue = AppConstants.PAGE_SIZE) int limit) {

        var response = productsService.findAll(name, page, limit);

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @Operation(operationId = "getProductById", description = "This end point is use to get a product")
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable UUID productId) throws ResourceNotFoundException {
        ProductsResponseDTO productsResponseDTO = productsService.findById(productId);

        if (productsResponseDTO.id() != null) {
            return new ResponseEntity<>(productsResponseDTO, HttpStatus.OK);
        } else {
            throw new ResourceNotFoundException("product not found with id: " + productId);

        }
    }

    @Operation(operationId = "createProduct", description = "This end point is use to create a product")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "creates and returns saved product",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ProductsResponseDTO.class))
                    )
            }
    )
    @PostMapping("/products")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<?> save(@Valid @RequestBody ProductsRequestDTO productsRequestDTO) {
        ProductsResponseDTO productsResponseDTO = productsService.createOne(productsRequestDTO);
        if (productsResponseDTO.id() != null) {
            return new ResponseEntity<>(productsResponseDTO, HttpStatus.CREATED);

        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "deleteProduct", description = "This end point is use to delete a product")
    @DeleteMapping("/products/{productId}")
    @CacheEvict(value = "products", key = "#id")
    public ResponseEntity<?> deleteOne(@PathVariable UUID productId) {

        try {
            productsService.deleteOne(productId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException();
        }

    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "updateProduct", description = "This end point is use to update a product")
    @PutMapping("/products/{productId}")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<?> updateProduct(@PathVariable UUID productId, @Valid @RequestBody ProductsRequestDTO requestDTO) {

        ProductsResponseDTO productsResponseDTO = productsService.updateOne(productId, requestDTO);

        if (productsResponseDTO.id() != null) {
            return new ResponseEntity<>(productsResponseDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);

        }

    }


}
