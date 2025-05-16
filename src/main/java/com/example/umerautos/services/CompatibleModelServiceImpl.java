package com.example.umerautos.services;

import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;
import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.repositories.CompatibleModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

        Optional<CompatibleModels> models = compatibleModelsRepository.findById(id);
        if (models.isPresent()){

            return CompatibleModelResponseDTO.mapToDTO(models.get());
        }else {
            return null;
        }
    }

    @Override
    public CompatibleModelResponseDTO updateOne(CompatibleModelRequestDTO requestDTO, UUID modelId) {
        Optional<CompatibleModels> models = compatibleModelsRepository.findById(modelId);
        if (models.isPresent()){

            models.get().setName(requestDTO.getName());

            CompatibleModels updateModel = compatibleModelsRepository.save(models.get());


            return CompatibleModelResponseDTO.mapToDTO(updateModel);
        }

        return null;
    }
}
