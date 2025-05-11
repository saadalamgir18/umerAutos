package com.example.umerautos.dto;

import com.example.umerautos.entities.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ProductsRequestDTO {


    private String name;


    private UUID brandId;


    private String sku;

    private String description;

    private int quantityInStock;

    private double purchasePrice;

    private double sellingPrice;


    private UUID shelfCodeId;
    private Set<UUID> compatibleModelIds; // just IDs for now


    public static Products mapToProducts(ProductsRequestDTO requestDTO) {
        return Products.builder()
                .name(requestDTO.getName())

                .build();
    }
}
