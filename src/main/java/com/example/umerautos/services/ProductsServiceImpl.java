package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.Brands;
import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductsServiceImpl implements ProductsService {


    @Autowired
    private BrandsRepository brandsRepository;
    @Autowired
    private CompatibleModelRepository compatibleModelsRepository;
    @Autowired
    private SuppliersRepository suppliersRepository;
    @Autowired
    private ShelfCodeRepository shelfCodeRepository;

    @Autowired
    private ProductsRepository productsRepo;


    @Override
    public ProductsResponseDTO createOne(ProductsRequestDTO products) {
        Set<CompatibleModels> models = new HashSet<>(compatibleModelsRepository.findAllById(products.compatibleModelIds()));


        Products newProduct = Products.builder()
                .name(products.name())
                .brand(brandsRepository.findById(products.brandId()).orElse(null))
                .quantityInStock(products.quantityInStock())
                .purchasePrice(products.purchasePrice())
                .sellingPrice(products.sellingPrice())
                .shelfCode(shelfCodeRepository.findById(products.shelfCodeId()).orElse(null))
                .compatibleModels(models)
                .build();


        Products savedProduct = productsRepo.save(newProduct);


        return ProductsResponseDTO.mapToDto(savedProduct);


    }

    @Override
    public PaginatedResponseDTO<ProductsResponseDTO> findAll(String productName, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Products> productsPage = productsRepo.findByName(productName, pageable);

        PaginationDTO pagination = new PaginationDTO(
                productsPage.getTotalElements(),
                productsPage.getTotalPages(),
                page,
                limit
        );

        List<ProductsResponseDTO> responseDTOS = productsPage
                .getContent()
                .stream()
                .map(ProductsResponseDTO::mapToDto)
                .collect(Collectors.toList());

        return new PaginatedResponseDTO<>(responseDTOS, pagination);
    }


    @Override
    public ProductsResponseDTO findById(UUID id) {
        Optional<Products> dbProduct = productsRepo.findById(id);

        if (dbProduct.isPresent()) {
            return ProductsResponseDTO.mapToDto(dbProduct.get());
        } else {
            return ProductsResponseDTO.builder().build();
        }
    }

    @Override
    public ProductsResponseDTO updateOne(UUID id, ProductsRequestDTO requestDTO) {


        Products product = productsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(requestDTO.name());
        product.setQuantityInStock(requestDTO.quantityInStock());
        product.setPurchasePrice(requestDTO.purchasePrice());
        product.setSellingPrice(requestDTO.sellingPrice());

        if (requestDTO.brandId() != null) {
            Brands brand = brandsRepository.findById(requestDTO.brandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);

        }
        if (requestDTO.shelfCodeId() != null) {
            ShelfCode shelfCode = shelfCodeRepository.findById(requestDTO.shelfCodeId())
                    .orElseThrow(() -> new RuntimeException("Shelf code not found"));
            product.setShelfCode(shelfCode);
        }
        if (requestDTO.compatibleModelIds() != null) {
            Set<CompatibleModels> models = new HashSet<>(
                    compatibleModelsRepository.findAllById(requestDTO.compatibleModelIds())
            );
            product.setCompatibleModels(models);
        }


        Products updatedProductResponse = productsRepo.save(product);

        return ProductsResponseDTO.mapToDto(updatedProductResponse);


    }

    @Override
    public void deleteOne(UUID id) throws ResourceNotFoundException {

        Optional<Products> products = productsRepo.findById(id);
        if (products.isPresent()) {

            productsRepo.deleteById(id);

        } else {
            throw new ResourceNotFoundException("product does not exist with id: " + id);

        }

    }

    public void updateStockQuantity(Optional<Products> products, SaleDTO saleDTO) {

        products.get().setQuantityInStock(products.get().getQuantityInStock() - saleDTO.quantitySold());

        productsRepo.save(products.get());
    }
}
