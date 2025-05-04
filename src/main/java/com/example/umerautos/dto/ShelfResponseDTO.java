package com.example.umerautos.dto;

import com.example.umerautos.entities.ShelfCode;
import lombok.*;

import java.util.Date;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelfResponseDTO {
    protected Date createdAt;

    protected Date updatedAt;

    private UUID id;

    private String name;

    public static ShelfResponseDTO mapToDTO(ShelfCode shelfCode){
        return ShelfResponseDTO
                .builder()
                .createdAt(shelfCode.getCreatedAt())
                .updatedAt(shelfCode.getUpdatedAt())
                .id(shelfCode.getId())
                .name(shelfCode.getName())
                .build();

    }
}
