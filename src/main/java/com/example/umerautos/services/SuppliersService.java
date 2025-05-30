package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;

import java.util.List;
import java.util.UUID;

public interface SuppliersService {
    PaginatedResponseDTO<SuppliersResponseDTO> findAll(int page, int limit);
    SuppliersResponseDTO  saveOne(SuppliersRequestDTO supplier);
    void deleteOne(UUID id);
//    SuppliersResponseDTO updateOne();
}
