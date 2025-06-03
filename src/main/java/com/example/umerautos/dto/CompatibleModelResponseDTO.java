package com.example.umerautos.dto;


import com.example.umerautos.entities.CompatibleModels;
import lombok.Builder;

import java.util.Date;
import java.util.UUID;


@Builder
public record CompatibleModelResponseDTO(
        UUID id, Date createdAt,Date updatedAt, String name
) {

    public static CompatibleModelResponseDTO mapToDTO(CompatibleModels models){
        return CompatibleModelResponseDTO.builder()
                .createdAt(models.getCreatedAt())
                .updatedAt(models.getUpdatedAt())
                .id(models.getId())
                .name(models.getName())
                .build();
    }




}
