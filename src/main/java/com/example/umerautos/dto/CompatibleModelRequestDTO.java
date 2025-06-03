package com.example.umerautos.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;


@Builder
public record CompatibleModelRequestDTO(
        @NotBlank(message = "brand name should not be null")
        @Size(min = 3, message = "name size must be greater then 3")
         String name
) {

}
