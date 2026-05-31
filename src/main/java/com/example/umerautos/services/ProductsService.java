package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.dto.SaleDTO;
import com.example.umerautos.entities.Products;
import com.example.umerautos.globalException.ResourceNotFoundException;

import java.util.Optional;

public interface ProductsService {

     ProductsResponseDTO createOne(ProductsRequestDTO products);
     PaginatedResponseDTO<ProductsResponseDTO> findAll(String productName, int page, int limit);
     ProductsResponseDTO findById(Long id);
     ProductsResponseDTO updateOne(Long id, ProductsRequestDTO requestDTO);
     void deleteOne(Long id) throws ResourceNotFoundException;
     void updateStockQuantity(Optional<Products> products, SaleDTO saleDTO);
}
