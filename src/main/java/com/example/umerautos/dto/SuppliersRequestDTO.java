package com.example.umerautos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;


@Builder
public record SuppliersRequestDTO(

        @NotNull(message = "company must not be null")

        String company,

        @NotNull(message = "contact person must not be null")

        String contactPerson,

        @Email(message = "provide valid email") String email,

        String phoneNumber) {

}
