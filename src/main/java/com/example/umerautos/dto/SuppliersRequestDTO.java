package com.example.umerautos.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SuppliersRequestDTO {
    private String company;

    private String contactPerson;

    private String email;

    private String phoneNumber;
}
