package com.example.umerautos.services;

import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.entities.Products;
import com.example.umerautos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductsServiceImpl implements ProductsService{


    @Autowired private BrandsRepository brandsRepository;
    @Autowired private CompatibleModelRepository compatibleModelsRepository;
    @Autowired  private CategoryRepository categoryRepository;
    @Autowired   private SuppliersRepository suppliersRepository;
    @Autowired  private ShelfCodeRepository shelfCodeRepository;

    @Autowired private ProductsRepository productsRepo;



    @Override
    public ProductsResponseDTO createOne(ProductsRequestDTO products) {


           Products newProduct  = Products.builder()
                .name(products.getName())
                .brand(brandsRepository.findById(products.getBrandId()).orElse(null))
//                .compatibleModels(compatibleModelsRepository.findById(products.getModelId()).orElse(null))
//                .category(categoryRepository.findById(products.getCategoryId()).orElse(null))
                .sku(products.getSku())
                .description(products.getDescription())
                .quantityInStock(products.getQuantityInStock())
                .purchasePrice(products.getPurchasePrice())
                .sellingPrice(products.getSellingPrice())
//                .supplierId(suppliersRepository.findById(products.getSupplierId()).orElse(null))
                .shelfCode(shelfCodeRepository.findById(products.getShelfCodeId()).orElse(null))
                .build();


           Products savedProduct = productsRepo.save(newProduct);


           return ProductsResponseDTO.mapToDto(savedProduct);



    }

    @Override
    public List<Products> findAll(String productName) {
        return productsRepo.findByName(productName);
    }

    @Override
    public ProductsResponseDTO findById(UUID id) {
        Optional<Products> dbProduct = productsRepo.findById(id);
        if (dbProduct.isPresent()){
            return ProductsResponseDTO.mapToDto(dbProduct.get());
        }else{
            return ProductsResponseDTO.builder().build();
        }
    }

    @Override
    public ProductsResponseDTO updateOne(UUID id) {
        return null;
    }

    @Override
    public void deleteOne(UUID id) {

        productsRepo.deleteById(id);
    }
}
