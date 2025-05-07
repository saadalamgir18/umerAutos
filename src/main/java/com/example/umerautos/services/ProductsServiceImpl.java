package com.example.umerautos.services;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.dto.SaleDTO;
import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.entities.Products;
import com.example.umerautos.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
        Set<CompatibleModels> models = new HashSet<>(compatibleModelsRepository.findAllById(products.getCompatibleModelIds()));



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
                .compatibleModels(models)
                .build();


           Products savedProduct = productsRepo.save(newProduct);


           return ProductsResponseDTO.mapToDto(savedProduct);



    }

    @Override
    public List<ProductsResponseDTO> findAll(String productName) {
       List<Products> products = productsRepo.findByName(productName);

        return products.stream()
                .map(ProductsResponseDTO::mapToDto)
                .collect(Collectors.toList());
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
    public ResponseEntity<?> deleteOne(UUID id) {

        Optional<Products> products = productsRepo.findById(id);
        if (products.isPresent()){

            productsRepo.deleteById(id);
            return CustomResponse.generateResponse(HttpStatus.OK, true, "product deleted successful!", null);

        }else {
            return CustomResponse.generateResponse(HttpStatus.NOT_FOUND, false, "product does not exit with id: " + id, null);


        }

    }

    public void updateStockQuantity(Optional<Products> products, SaleDTO saleDTO) {

        products.get().setQuantityInStock(products.get().getQuantityInStock() -  saleDTO.getQuantitySold());

        productsRepo.save(products.get());
    }
}
