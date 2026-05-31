package com.example.umerautos.services;

import com.example.umerautos.dto.CustomerRequestDto;
import com.example.umerautos.dto.CustomerResponseDto;
import com.example.umerautos.dto.PaginatedResponseDTO;
import org.springframework.data.domain.Pageable;

public interface CustomerService {
    CustomerResponseDto create(CustomerRequestDto customerRequestDto);

    CustomerResponseDto getById(Long id);

    PaginatedResponseDTO<CustomerResponseDto> getAll(Pageable pageable, String name);

    CustomerResponseDto update(Long id, CustomerRequestDto customerRequestDto);

    void delete(Long id);
}
