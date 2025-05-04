package com.example.umerautos.services;

import com.example.umerautos.entities.Brands;
import com.example.umerautos.repositories.BrandsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandsServiceImpl implements BrandsService{

    private BrandsRepository brandsRepository;

    public BrandsServiceImpl(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    @Override
    public List<Brands> findAll() {
        return brandsRepository.findAll();
    }

    @Override
    public Optional<Brands> findOne(UUID id) {
        return brandsRepository.findById(id);
    }

    @Override
    public Brands createOne(Brands brands) {
        return brandsRepository.save(brands);
    }
}
