package com.example.umerautos.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record ShelfRequestDTO(
        @NotNull(message = "name must not be null")
        String name
) {


}
