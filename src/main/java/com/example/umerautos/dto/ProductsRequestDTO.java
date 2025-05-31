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


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@ValidSellingPrice
public class ProductsRequestDTO {


    @NotBlank(message = "product name is mandatory")
    @Size(min = 3)
    private String name;

    @NotNull(message = "brandId is mandatory")
    private UUID brandId;

    @NotBlank(message = "sku is mandatory")
    private String sku;

    private String description;

    @Min(value = 1, message = "purchase price should be greater then 1")
    private int quantityInStock;

    @Min(value = 1, message = "purchase price should be greater then 1")
    private int purchasePrice;

    private int sellingPrice;

    @NotNull(message = "shelfCode is mandatory")
    private UUID shelfCodeId;

    @NotEmpty(message = "Compatible model cannot be empty")
    private Set<UUID> compatibleModelIds; // just IDs for now


    public static Products mapToProducts(ProductsRequestDTO requestDTO) {
        return Products.builder()
                .name(requestDTO.getName())

                .build();
    }
}
