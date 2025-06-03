package com.example.umerautos.dto;

import com.example.umerautos.entities.*;
import com.example.umerautos.validation.ValidSellingPrice;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;


@Builder
@ValidSellingPrice
public record ProductsRequestDTO(
        @NotBlank(message = "product name is mandatory")
        @Size(min = 3)
        String name,
        @NotNull(message = "brandId is mandatory")
        UUID brandId,
        @NotBlank(message = "sku is mandatory")
        String sku,
        String description,
        @Min(value = 1, message = "purchase price should be greater then 1")
        int quantityInStock,
        @Min(value = 1, message = "purchase price should be greater then 1")
        int purchasePrice,
        int sellingPrice,
        @NotNull(message = "shelfCode is mandatory")
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
