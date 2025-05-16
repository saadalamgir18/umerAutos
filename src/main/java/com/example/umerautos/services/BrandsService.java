package com.example.umerautos.services;

import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.entities.Brands;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandsService {
    public List<BrandsResponseDTO> findAll();
    public BrandsResponseDTO findOne(UUID id);

    public BrandsResponseDTO createOne(BrandsRequestDTO brands);
}
