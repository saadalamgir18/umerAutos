package com.example.umerautos.services;

import com.example.umerautos.dto.ShelfResponseDTO;
import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.repositories.ShelfCodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShelfServiceImpl implements ShelfService{
    private final ShelfCodeRepository shelfCodeRepository;

    public ShelfServiceImpl(ShelfCodeRepository shelfCodeRepository) {
        this.shelfCodeRepository = shelfCodeRepository;
    }

    @Override
    public ShelfResponseDTO createOne(ShelfCode shelfCode) {
        ShelfCode newShelfCode = shelfCodeRepository.save(shelfCode);
        return ShelfResponseDTO.mapToDTO(newShelfCode);
    }

    @Override
    public List<ShelfResponseDTO> findAll() {
        List<ShelfCode> shelfCode =  shelfCodeRepository.findAll();
        return shelfCode.stream().map(ShelfResponseDTO::mapToDTO).collect(Collectors.toList());
    }
}
