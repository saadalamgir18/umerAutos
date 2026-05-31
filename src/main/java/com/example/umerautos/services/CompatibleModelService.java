package com.example.umerautos.services;

import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;

import java.util.Set;

public interface CompatibleModelService {

    public CompatibleModelResponseDTO createOne(CompatibleModelRequestDTO models);

    public Set<CompatibleModelResponseDTO> findAll();

    public CompatibleModelResponseDTO findOne(Long id);


    CompatibleModelResponseDTO updateOne(CompatibleModelRequestDTO requestDTO, Long modelId);
}
