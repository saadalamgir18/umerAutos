package com.example.umerautos.services;

import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
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
        return brands.map(BrandsResponseDTO::mapTo).orElse(null);
    }

    @Override
    public BrandsResponseDTO createOne(BrandsRequestDTO brands) {

        Brands requestBrands = Brands
                .builder()
                .name(brands.getName())
                .build();
        Brands newBrand =  brandsRepository.save(requestBrands);
        return BrandsResponseDTO.mapTo(newBrand);
    }

    @Override
    public BrandsResponseDTO updateOne(BrandsRequestDTO brands, UUID id) {
        Optional<Brands> db_brands = brandsRepository.findById(id);
        if (db_brands.isPresent()){

            db_brands.get().setName(brands.getName());

            Brands updatedBrands = brandsRepository.save(db_brands.get());
            return BrandsResponseDTO.mapTo(updatedBrands);
        }else {
            return null;
        }
    }
}
