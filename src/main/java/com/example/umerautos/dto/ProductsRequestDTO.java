package com.example.umerautos.dto;

import com.example.umerautos.entities.Products;
import jakarta.validation.constraints.*;
import lombok.Builder;

import java.util.Set;


@Builder
public record ProductsRequestDTO(
        @NotBlank(message = "product name is required")
        @Size(min = 3, message = "name length should be greater then 3 characters")
        String name,

        @NotNull(message = "brandId is required")
        Long brandId,

        @PositiveOrZero(message = "quantity in stock should be positive")
        int quantityInStock,

        @Min(value = 1, message = "purchase price should be greater then 1")
        int purchasePrice,

//        @ValidSellingPrice
        int sellingPrice,

        @NotNull(message = "shelfCode is required")
        Long shelfCodeId,

        @NotEmpty(message = "Compatible model cannot be empty")
        Set<Long> compatibleModelIds
) {

    public static Products mapToProducts(ProductsRequestDTO requestDTO) {
        return Products.builder()
                .name(requestDTO.name())

                .build();
    }
}
