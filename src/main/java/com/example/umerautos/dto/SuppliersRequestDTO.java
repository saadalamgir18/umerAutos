package com.example.umerautos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SuppliersRequestDTO {

    @NotNull(message = "company must not be null")

    private String company;

    @NotNull(message = "contact person must not be null")

    private String contactPerson;

    @Email(message = "provide valid email")
    private String email;

    private String phoneNumber;
}
