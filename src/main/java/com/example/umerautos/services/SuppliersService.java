package com.example.umerautos.services;

import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SuppliersService {
    List<SuppliersResponseDTO> findAll();
    SuppliersResponseDTO  saveOne(SuppliersRequestDTO supplier);
    void deleteOne(UUID id);
//    SuppliersResponseDTO updateOne();
}
