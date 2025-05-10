package com.example.umerautos.dto;

import com.example.umerautos.entities.Suppliers;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class SuppliersResponseDTO {
    private String company;

    private String contactPerson;

    private String email;

    private String phoneNumber;

    public static SuppliersResponseDTO mapToDTO(Suppliers suppliers){
        return SuppliersResponseDTO
                .builder()
                .company(suppliers.getCompany())
                .contactPerson(suppliers.getContactPerson())
                .email(suppliers.getEmail())
                .phoneNumber(suppliers.getEmail())
                .build();
    }

}
