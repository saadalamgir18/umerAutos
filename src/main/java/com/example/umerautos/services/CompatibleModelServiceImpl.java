package com.example.umerautos.services;

import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;
import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.repositories.CompatibleModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CompatibleModelServiceImpl implements CompatibleModelService{
    @Autowired
    private CompatibleModelRepository compatibleModelsRepository;

    @Override
    public CompatibleModelResponseDTO createOne(CompatibleModelRequestDTO models) {


        CompatibleModels newModel = compatibleModelsRepository.save(CompatibleModels
                .builder()
                .name(models.getName())
                .build());

        return CompatibleModelResponseDTO.mapToDTO(newModel);
    }

    @Override
    public Set<CompatibleModelResponseDTO> findAll() {
        List<CompatibleModels> models = compatibleModelsRepository.findAll();
        return models.stream().map(CompatibleModelResponseDTO::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public CompatibleModelResponseDTO findOne(UUID id) {
        return null;
    }
}
