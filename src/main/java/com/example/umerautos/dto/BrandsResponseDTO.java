package com.example.umerautos.dto;

import com.example.umerautos.entities.Brands;
import jakarta.persistence.Column;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class BrandsResponseDTO {
    protected Date createdAt;

    protected Date updatedAt;

    private UUID id;

    private String name;

    public static BrandsResponseDTO mapTo(Brands brand){
        return BrandsResponseDTO
                .builder()
                .createdAt(brand.getCreatedAt())
                .updatedAt(brand.getUpdatedAt())
                .id(brand.getId())
                .name(brand.getName())
                .build();
    }


}
