package com.example.umerautos.services;

import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.entities.Brands;
import com.example.umerautos.repositories.BrandsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandsServiceImpl implements BrandsService{

    private final BrandsRepository brandsRepository;

    public BrandsServiceImpl(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    @Override
    public List<BrandsResponseDTO> findAll() {
        List<Brands> allBrands =  brandsRepository.findAll();

        return allBrands.stream().map(BrandsResponseDTO::mapTo).collect(Collectors.toList());

    }

    @Override
    public BrandsResponseDTO findOne(UUID id) {
        Optional<Brands> brands = brandsRepository.findById(id);
        if (brands.isPresent()){

            return BrandsResponseDTO.mapTo(brands.get());

        }else {
            return null;
        }
    }

    @Override
    public BrandsResponseDTO createOne(Brands brands) {
        Brands newBrand =  brandsRepository.save(brands);
        return BrandsResponseDTO.mapTo(newBrand);
    }
}
