package com.example.umerautos.services;

import com.example.umerautos.dto.CustomerResponseDto;

import java.util.List;

public interface CustomersService {
    List<CustomerResponseDto> findAll();
}
