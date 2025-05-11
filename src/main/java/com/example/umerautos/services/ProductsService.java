package com.example.umerautos.services;

import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.dto.SaleDTO;
import com.example.umerautos.entities.Products;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductsService {

     ProductsResponseDTO createOne(ProductsRequestDTO products);
     List<ProductsResponseDTO> findAll(String productName);
     ProductsResponseDTO findById(UUID id);
     ProductsResponseDTO updateOne(UUID id, ProductsRequestDTO requestDTO);
     ResponseEntity<?> deleteOne(UUID id);
     void updateStockQuantity(Optional<Products> products, SaleDTO saleDTO);
}
