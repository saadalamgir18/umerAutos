package com.example.umerautos.services;

import com.example.umerautos.dto.ShelfResponseDTO;
import com.example.umerautos.entities.ShelfCode;

import java.util.List;

public interface ShelfService {
    ShelfResponseDTO createOne(ShelfCode shelfCode);

    List<ShelfResponseDTO> findAll();
}
