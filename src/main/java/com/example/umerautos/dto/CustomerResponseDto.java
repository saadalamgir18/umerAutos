package com.example.umerautos.dto;

import com.example.umerautos.entities.Customer;
import lombok.Builder;

import java.util.Date;

@Builder
public record CustomerResponseDto(
        Long id,
        String name,
        String idCardNo,
        Date createdAt,
        Date updatedAt
) {
    public static CustomerResponseDto mapToDto(Customer customer) {
        return CustomerResponseDto.builder()
                .id(customer.getId())
                .name(customer.getName())
                .idCardNo(customer.getIdCardNo())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .build();
    }
}
