package com.example.umerautos.services;

import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;
import com.example.umerautos.entities.CompatibleModels;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface CompatibleModelService {

    public CompatibleModelResponseDTO createOne(CompatibleModelRequestDTO models);

    public Set<CompatibleModelResponseDTO> findAll();

    public CompatibleModelResponseDTO findOne(UUID id);



}
