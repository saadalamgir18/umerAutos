package com.example.umerautos.dto;


import com.example.umerautos.entities.CompatibleModels;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CompatibleModelResponseDTO {
    private UUID id;

    protected Date createdAt;

    protected Date updatedAt;
    private String name;

    public static CompatibleModelResponseDTO mapToDTO(CompatibleModels models){
        return CompatibleModelResponseDTO.builder()
                .createdAt(models.getCreatedAt())
                .updatedAt(models.getUpdatedAt())
                .id(models.getId())
                .name(models.getName())
                .build();
    }




}
