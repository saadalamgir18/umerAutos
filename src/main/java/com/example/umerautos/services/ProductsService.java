package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.dto.SaleDTO;
import com.example.umerautos.entities.Products;
import com.example.umerautos.globalException.ResourceNotFoundException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface ProductsService {

     ProductsResponseDTO createOne(ProductsRequestDTO products);
     PaginatedResponseDTO<ProductsResponseDTO> findAll(String productName, int page, int limit);
     ProductsResponseDTO findById(UUID id);
     ProductsResponseDTO updateOne(UUID id, ProductsRequestDTO requestDTO);
     void deleteOne(UUID id) throws ResourceNotFoundException;
     void updateStockQuantity(Optional<Products> products, SaleDTO saleDTO);
}
