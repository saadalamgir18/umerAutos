package com.example.umerautos.dto;

import com.example.umerautos.entities.Brands;
import lombok.Builder;

import java.util.Date;

@Builder
public record BrandsResponseDTO(
        Date createdAt,
        Date updatedAt,
        Long id, String name

) {

    public static BrandsResponseDTO mapTo(Brands brand) {
        return BrandsResponseDTO
                .builder()
                .createdAt(brand.getCreatedAt())
                .updatedAt(brand.getUpdatedAt())
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }


}
