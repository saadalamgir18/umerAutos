package com.example.umerautos.dto;


import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.entities.Products;
import lombok.Builder;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
public record ProductsResponseDTO(
        Date createdAt,
        Date updatedAt,
        Long id,
        String name,
        int quantityInStock,
        int purchasePrice,
        int sellingPrice,
        Long brandId,
        String brandName,
        Long modelId,
        String modelName,
        Long categoryId,
        String categoryName,
        Long supplierId,
        String supplierName,
        Long shelfCodeId,
        String shelfCodeName,
        List<Long> compatibleModelsIds,
        Set<String> compatibleModels


) {


    public static ProductsResponseDTO mapToDto(Products product) {
        return ProductsResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .quantityInStock(product.getQuantityInStock())
                .purchasePrice(product.getPurchasePrice())
                .sellingPrice(product.getSellingPrice())

                .brandId(product.getBrand().getId())
                .brandName(product.getBrand().getName())
                .shelfCodeId(product.getShelfCode().getId())
                .shelfCodeName(product.getShelfCode().getName())
                .createdAt(product.getCreatedAt())
                .updatedAt(product.getUpdatedAt())
                .compatibleModelsIds(product.getCompatibleModels().stream().map(CompatibleModels::getId).toList())
                .compatibleModels(product.getCompatibleModels().stream().map(CompatibleModels::getName).collect(Collectors.toSet()))

                .build();

    }
}
