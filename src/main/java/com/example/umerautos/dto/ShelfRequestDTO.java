package com.example.umerautos.dto;


import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ShelfRequestDTO {

    @NotNull(message = "name must not be null")
    private String name;
}
