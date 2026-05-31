package com.example.umerautos.services;

import com.example.umerautos.dto.CompatibleModelRequestDTO;
import com.example.umerautos.dto.CompatibleModelResponseDTO;
import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.repositories.CompatibleModelRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CompatibleModelServiceImpl implements CompatibleModelService {
    private final CompatibleModelRepository compatibleModelsRepository;
    private static final Logger logger = LoggerFactory.getLogger(CompatibleModelServiceImpl.class);

    public CompatibleModelServiceImpl(CompatibleModelRepository compatibleModelsRepository) {
        this.compatibleModelsRepository = compatibleModelsRepository;
    }

    @Override
    public CompatibleModelResponseDTO createOne(CompatibleModelRequestDTO models) {
        logger.info("Creating new compatible model: {}", models.name());
        CompatibleModels newModel = compatibleModelsRepository.save(CompatibleModels
                .builder()
                .name(models.name())
                .build());
        logger.info("Compatible model created with id: {}", newModel.getId());
        return CompatibleModelResponseDTO.mapToDTO(newModel);
    }

    @Override
    public Set<CompatibleModelResponseDTO> findAll() {
        logger.info("Fetching all compatible models");
        List<CompatibleModels> models = compatibleModelsRepository.findAll();
        logger.info("Found {} compatible models", models.size());
        return models.stream().map(CompatibleModelResponseDTO::mapToDTO).collect(Collectors.toSet());
    }

    @Override
    public CompatibleModelResponseDTO findOne(Long id) {
        logger.info("Fetching compatible model with id: {}", id);
        Optional<CompatibleModels> models = compatibleModelsRepository.findById(id);
        if (models.isPresent()) {
            logger.info("Compatible model found: {}", models.get().getName());
            return CompatibleModelResponseDTO.mapToDTO(models.get());
        } else {
            logger.warn("Compatible model not found with id: {}", id);
            return null;
        }
    }

    @Override
    public CompatibleModelResponseDTO updateOne(CompatibleModelRequestDTO requestDTO, Long modelId) {
        logger.info("Updating compatible model with id: {}", modelId);
        Optional<CompatibleModels> models = compatibleModelsRepository.findById(modelId);
        if (models.isPresent()) {
            logger.info("Compatible model found for update: {}", models.get().getName());
            models.get().setName(requestDTO.name());

            CompatibleModels updateModel = compatibleModelsRepository.save(models.get());
            logger.info("Compatible model updated successfully");

            return CompatibleModelResponseDTO.mapToDTO(updateModel);
        }
        logger.warn("Compatible model not found for update with id: {}", modelId);
        return null;
    }
}
