package com.example.umerautos.dto;

import com.example.umerautos.entities.Suppliers;
import lombok.Builder;

@Builder
public record SuppliersResponseDTO(

        String company,

        String contactPerson,

        String email,

        String phoneNumber
) {

    public static SuppliersResponseDTO mapToDTO(Suppliers suppliers) {
        return SuppliersResponseDTO
                .builder()
                .company(suppliers.getCompany())
                .contactPerson(suppliers.getContactPerson())
                .email(suppliers.getEmail())
                .phoneNumber(suppliers.getEmail())
                .build();
    }

}
