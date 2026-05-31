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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(description = "These end points has rest controller for products", name = "Product Controller")
public class ProductController {

    private final ProductsService productsService;
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);


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
        logger.info("Request received to fetch all products. Name: {}, Page: {}, Limit: {}", name, page, limit);
        try {
            var response = productsService.findAll(name, page, limit);
            logger.info("Successfully fetched products");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error fetching products", e);
            throw e;
        }
    }


    @Operation(operationId = "getProductById", description = "This end point is use to get a product")
    @GetMapping("/products/{productId}")
    public ResponseEntity<?> findProductById(@PathVariable Long productId) throws ResourceNotFoundException {
        logger.info("Request received to fetch product with ID: {}", productId);
        try {
            ProductsResponseDTO productsResponseDTO = productsService.findById(productId);

            if (productsResponseDTO.id() != null) {
                logger.info("Successfully fetched product with ID: {}", productId);
                return new ResponseEntity<>(productsResponseDTO, HttpStatus.OK);
            } else {
                logger.warn("Product not found with ID: {}", productId);
                throw new ResourceNotFoundException("product not found with id: " + productId);
            }
        } catch (Exception e) {
            logger.error("Error fetching product with ID: {}", productId, e);
            throw e;
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
        logger.info("Request received to create product: {}", productsRequestDTO.name());
        try {
            ProductsResponseDTO productsResponseDTO = productsService.createOne(productsRequestDTO);
            if (productsResponseDTO.id() != null) {
                logger.info("Successfully created product with ID: {}", productsResponseDTO.id());
                return new ResponseEntity<>(productsResponseDTO, HttpStatus.CREATED);
            } else {
                logger.error("Failed to create product: {}", productsRequestDTO.name());
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.error("Error creating product: {}", productsRequestDTO.name(), e);
            throw e;
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "deleteProduct", description = "This end point is use to delete a product")
    @DeleteMapping("/products/{productId}")
    @CacheEvict(value = "products", key = "#productId")
    public ResponseEntity<?> deleteOne(@PathVariable Long productId) {
        logger.info("Request received to delete product with ID: {}", productId);
        try {
            productsService.deleteOne(productId);
            logger.info("Successfully deleted product with ID: {}", productId);
            return new ResponseEntity<>(null, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error deleting product with ID: {}", productId, e);
            throw new RuntimeException();
        }
    }

    //    @PreAuthorize("hasRole('ADMIN')")
    @Operation(operationId = "updateProduct", description = "This end point is use to update a product")
    @PutMapping("/products/{productId}")
    @CacheEvict(value = "products", allEntries = true)
    public ResponseEntity<ProductsResponseDTO> updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductsRequestDTO requestDTO) {
        logger.info("Request received to update product with ID: {}", productId);
        try {
            ProductsResponseDTO productsResponseDTO = productsService.updateOne(productId, requestDTO);

            if (productsResponseDTO.id() != null) {
                logger.info("Successfully updated product with ID: {}", productId);
                return new ResponseEntity<>(productsResponseDTO, HttpStatus.OK);
            } else {
                logger.error("Failed to update product with ID: {}", productId);
                return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            logger.info("Error updating product with ID: {}", productId, e);
            throw e;
        }
    }
}
