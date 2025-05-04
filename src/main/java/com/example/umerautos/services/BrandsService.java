package com.example.umerautos.services;

import com.example.umerautos.entities.Brands;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandsService {
    public List<Brands> findAll();
    public Optional<Brands> findOne(UUID id);

    public Brands createOne(Brands brands);
}
