package com.example.umerautos.dto;


import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.entities.Products;
import lombok.Builder;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Builder
public record ProductsResponseDTO(
        Date createdAt,
        Date updatedAt,
        UUID id,
        String name,
        int quantityInStock, int purchasePrice, int sellingPrice, UUID brandId,
        String brandName, UUID modelId, String modelName, UUID categoryId, String categoryName,
        UUID supplierId, String supplierName, UUID shelfCodeId, String shelfCodeName, List<UUID> compatibleModelsIds,
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
