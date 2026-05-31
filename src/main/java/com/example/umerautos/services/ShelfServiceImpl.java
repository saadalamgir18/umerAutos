package com.example.umerautos.services;

import com.example.umerautos.dto.ShelfResponseDTO;
import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.ShelfCodeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShelfServiceImpl implements ShelfService {
    private final ShelfCodeRepository shelfCodeRepository;
    private static final Logger logger = LoggerFactory.getLogger(ShelfServiceImpl.class);

    public ShelfServiceImpl(ShelfCodeRepository shelfCodeRepository) {
        this.shelfCodeRepository = shelfCodeRepository;
    }

    @Override
    public ShelfResponseDTO createOne(ShelfCode shelfCode) {
        logger.info("Creating new shelf code: {}", shelfCode.getName());
        ShelfCode newShelfCode = shelfCodeRepository.save(shelfCode);
        logger.info("Shelf code created with id: {}", newShelfCode.getId());
        return ShelfResponseDTO.mapToDTO(newShelfCode);
    }

    @Override
    public List<ShelfResponseDTO> findAll() {
        logger.info("Fetching all shelf codes");
        List<ShelfCode> shelfCode = shelfCodeRepository.findAll();
        logger.info("Found {} shelf codes", shelfCode.size());
        return shelfCode.stream().map(ShelfResponseDTO::mapToDTO).toList();
    }

    @Override
    public ShelfResponseDTO updateOne(Long id, ShelfCode shelfCode) {
        logger.info("Updating shelf code with id: {}", id);
        Optional<ShelfCode> existingShelfCode = shelfCodeRepository.findById(id);
        if (existingShelfCode.isPresent()) {
            existingShelfCode.get().setName(shelfCode.getName());
            ShelfCode updatedShelfCode = shelfCodeRepository.save(existingShelfCode.get());
            logger.info("Shelf code updated successfully");
            return ShelfResponseDTO.mapToDTO(updatedShelfCode);
        }
        logger.warn("Shelf code not found for update with id: {}", id);
        return null;
    }

    @Override
    public void deleteOne(Long id) {
        logger.info("Deleting shelf code with id: {}", id);
        if (!shelfCodeRepository.existsById(id)) {
            logger.error("Shelf code not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Shelf code not found with id: " + id);
        }
        shelfCodeRepository.deleteById(id);
        logger.info("Shelf code deleted successfully");
    }
}
