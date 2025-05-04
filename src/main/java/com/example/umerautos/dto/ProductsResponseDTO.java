package com.example.umerautos.dto;


import com.example.umerautos.entities.Products;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsResponseDTO {

    protected Date createdAt;

    protected Date updatedAt;

    private UUID id;
    private String name;
    private String sku;
    private String description;
    private int quantityInStock;
    private double purchasePrice;
    private double sellingPrice;

    // Instead of full entity, return only key data
    private UUID brandId;
    private String brandName;

    private UUID modelId;
    private String modelName;

    private UUID categoryId;
    private String categoryName;

    private UUID supplierId;
    private String supplierName;

    private UUID shelfCodeId;
    private String shelfCodeName;


    public static ProductsResponseDTO mapToDto(Products product) {
        return ProductsResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .sku(product.getSku())
                .description(product.getDescription())
                .quantityInStock(product.getQuantityInStock())
                .purchasePrice(product.getPurchasePrice())
                .sellingPrice(product.getSellingPrice())

                .brandId(product.getBrand().getId())
                .brandName(product.getBrand().getName())

//                .modelId(product.getCompatibleModels().getId())
//                .modelName(product.getCompatibleModels().getName())

//                .categoryId(product.getCategory().getId())
//                .categoryName(product.getCategory().getName())

//                .supplierId(product.getSupplierId().getId())
//                .supplierName(product.getSupplierId().getCompany())

                .shelfCodeId(product.getShelfCode().getId())
                .shelfCodeName(product.getShelfCode().getName())

                .build();

    }
}
