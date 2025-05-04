package com.example.umerautos.services;

import com.example.umerautos.entities.ShelfCode;

import java.util.List;

public interface ShelfService {
    ShelfCode createOne(ShelfCode shelfCode);

    List<ShelfCode> findAll();
}
