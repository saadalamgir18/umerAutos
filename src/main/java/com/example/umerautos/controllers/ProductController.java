package com.example.umerautos.controllers;

import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.entities.Products;
import com.example.umerautos.services.ProductsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class ProductController {

    private ProductsService productsService;

    public ProductController(ProductsService productsService) {
        this.productsService = productsService;
    }

    @GetMapping("/products")
    public List<Products> findAll(@RequestParam(required = false) String name){
        System.out.println(name);
        return productsService.findAll(name);
    }
    @GetMapping("/products/{productId}")
    public ProductsResponseDTO findAll(@PathVariable UUID productId){
        return productsService.findById(productId);
    }

    @PostMapping("/products")
    public ProductsResponseDTO save(@RequestBody ProductsRequestDTO productsRequestDTO){
        System.out.println(productsRequestDTO);
        return productsService.createOne(productsRequestDTO);
    }

    @DeleteMapping("/products/{productId}")
    public void deleteOne(@PathVariable UUID productId){
        System.out.println("deleting product on this id: " + productId);

        productsService.deleteOne(productId);

    }
}
