package com.example.umerautos.dto;

import com.example.umerautos.entities.Products;
import com.example.umerautos.validation.ValidSellingPrice;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.Set;
import java.util.UUID;


@Builder
public record ProductsRequestDTO(
        @NotBlank(message = "product name is required")
        @Size(min = 3, message = "name length should be greater then 3 characters")
        String name,

        @NotNull(message = "brandId is required")
        UUID brandId,

        @NotBlank(message = "sku is required")
        String sku,

        String description,

        @PositiveOrZero(message = "quantity in stock should be positive")
        int quantityInStock,

        @Min(value = 1, message = "purchase price should be greater then 1")
        int purchasePrice,

        @ValidSellingPrice
        int sellingPrice,

        @NotNull(message = "shelfCode is required")
        UUID shelfCodeId,

        @NotEmpty(message = "Compatible model cannot be empty")
        Set<UUID> compatibleModelIds
) {

    public static Products mapToProducts(ProductsRequestDTO requestDTO) {
        return Products.builder()
                .name(requestDTO.name())

                .build();
    }
}
