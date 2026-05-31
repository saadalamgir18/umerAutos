package com.example.umerautos.services;

import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.SuppliersRequestDTO;
import com.example.umerautos.dto.SuppliersResponseDTO;

public interface SuppliersService {
    PaginatedResponseDTO<SuppliersResponseDTO> findAll(int page, int limit);
    SuppliersResponseDTO  saveOne(SuppliersRequestDTO supplier);
    void deleteOne(Long id);
//    SuppliersResponseDTO updateOne();
}
