package com.example.umerautos.dto;

import com.example.umerautos.entities.ShelfCode;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;


@Builder
public record ShelfResponseDTO(
        Date createdAt,

        Date updatedAt,
        UUID id,
        String name
) {


    public static ShelfResponseDTO mapToDTO(ShelfCode shelfCode) {
        return ShelfResponseDTO
                .builder()
                .createdAt(shelfCode.getCreatedAt())
                .updatedAt(shelfCode.getUpdatedAt())
                .id(shelfCode.getId())
                .name(shelfCode.getName())
                .build();

    }
}
