package com.example.umerautos.services;

import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.entities.Products;

import java.util.List;
import java.util.UUID;

public interface ProductsService {

    public ProductsResponseDTO createOne(ProductsRequestDTO products);
    public List<Products> findAll(String productName);
    public ProductsResponseDTO findById(UUID id);
    public ProductsResponseDTO updateOne(UUID id);
    public void deleteOne(UUID id);
}
