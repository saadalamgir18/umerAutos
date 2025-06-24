package com.example.umerautos.services;

import com.example.umerautos.dto.BrandsRequestDTO;
import com.example.umerautos.dto.BrandsResponseDTO;
import com.example.umerautos.entities.Brands;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.BrandsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BrandsServiceImpl implements BrandsService {

    private final BrandsRepository brandsRepository;

    Logger log = LoggerFactory.getLogger(BrandsServiceImpl.class);

    public BrandsServiceImpl(BrandsRepository brandsRepository) {
        this.brandsRepository = brandsRepository;
    }

    @Override
    public List<BrandsResponseDTO> findAll() {
        List<Brands> allBrands = brandsRepository.findAll();

        return allBrands.stream().map(brands -> new BrandsResponseDTO(brands.getCreatedAt(), brands.getUpdatedAt(), brands.getId(), brands.getName())).collect(Collectors.toList());

    }

    @Override
    public BrandsResponseDTO findOne(UUID id) {
        Optional<Brands> brands = brandsRepository.findById(id);
        return brands.map(BrandsResponseDTO::mapTo).orElseThrow(() -> new ResourceNotFoundException("brand does not exist with id: " + id));
    }

    @Override
    public BrandsResponseDTO createOne(BrandsRequestDTO brands) {

        Brands requestBrands = Brands
                .builder()
                .name(brands.name())
                .build();
        Brands newBrand = brandsRepository.save(requestBrands);
        return BrandsResponseDTO.mapTo(newBrand);
    }

    @Override
    public BrandsResponseDTO updateOne(BrandsRequestDTO brands, UUID id) {
        Optional<Brands> db_brands = brandsRepository.findById(id);
        if (db_brands.isPresent()) {

            db_brands.get().setName(brands.name());

            Brands updatedBrands = brandsRepository.save(db_brands.get());
            return BrandsResponseDTO.mapTo(updatedBrands);
        } else {
            return null;
        }
    }
}
