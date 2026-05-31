package com.example.umerautos.services;

import com.example.umerautos.dto.ShelfResponseDTO;
import com.example.umerautos.entities.ShelfCode;

import java.util.List;

public interface ShelfService {
    ShelfResponseDTO createOne(ShelfCode shelfCode);

    List<ShelfResponseDTO> findAll();

    ShelfResponseDTO updateOne(Long id, ShelfCode shelfCode);

    void deleteOne(Long id);
}
