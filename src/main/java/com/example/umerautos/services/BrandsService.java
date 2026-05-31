package com.example.umerautos.services;

import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;

import java.util.List;

public interface BrandsService {
    public List<BrandsResponseDTO> findAll();
    public BrandsResponseDTO findOne(Long id);

    public BrandsResponseDTO createOne(BrandsRequestDTO brands);
    public BrandsResponseDTO updateOne(BrandsRequestDTO brands, Long id);
}
