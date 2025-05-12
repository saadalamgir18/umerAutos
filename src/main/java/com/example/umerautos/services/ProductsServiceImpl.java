package com.example.umerautos.services;

import com.example.umerautos.customresponse.CustomResponse;
import com.example.umerautos.dto.ProductsRequestDTO;
import com.example.umerautos.dto.ProductsResponseDTO;
import com.example.umerautos.dto.SaleDTO;
import com.example.umerautos.entities.Brands;
import com.example.umerautos.entities.CompatibleModels;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.ShelfCode;
import com.example.umerautos.globalException.ResourceNotFoundException;
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
    @Autowired   private SuppliersRepository suppliersRepository;
    @Autowired  private ShelfCodeRepository shelfCodeRepository;

    @Autowired private ProductsRepository productsRepo;



    @Override
    public ProductsResponseDTO createOne(ProductsRequestDTO products) {
        Set<CompatibleModels> models = new HashSet<>(compatibleModelsRepository.findAllById(products.getCompatibleModelIds()));



        Products newProduct  = Products.builder()
                .name(products.getName())
                .brand(brandsRepository.findById(products.getBrandId()).orElse(null))
                .sku(products.getSku())
                .description(products.getDescription())
                .quantityInStock(products.getQuantityInStock())
                .purchasePrice(products.getPurchasePrice())
                .sellingPrice(products.getSellingPrice())
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
    public ProductsResponseDTO updateOne(UUID id, ProductsRequestDTO requestDTO) {


        Products product = productsRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setName(requestDTO.getName());
        product.setSku(requestDTO.getSku());
        product.setDescription(requestDTO.getDescription());
        product.setQuantityInStock(requestDTO.getQuantityInStock());
        product.setPurchasePrice(requestDTO.getPurchasePrice());
        product.setSellingPrice(requestDTO.getSellingPrice());

        if (requestDTO.getBrandId() != null) {
            Brands brand = brandsRepository.findById(requestDTO.getBrandId())
                    .orElseThrow(() -> new RuntimeException("Brand not found"));
            product.setBrand(brand);

        }
        if (requestDTO.getShelfCodeId() != null) {
            ShelfCode shelfCode = shelfCodeRepository.findById(requestDTO.getShelfCodeId())
                    .orElseThrow(() -> new RuntimeException("Shelf code not found"));
            product.setShelfCode(shelfCode);
        }
        if (requestDTO.getCompatibleModelIds() != null) {
            Set<CompatibleModels> models = new HashSet<>(
                    compatibleModelsRepository.findAllById(requestDTO.getCompatibleModelIds())
            );
            product.setCompatibleModels(models);
        }



            Products updatedProductResponse = productsRepo.save(product);

            return ProductsResponseDTO.mapToDto(updatedProductResponse);


    }

    @Override
    public void deleteOne(UUID id) throws ResourceNotFoundException {

        Optional<Products> products = productsRepo.findById(id);
        if (products.isPresent()){

            productsRepo.deleteById(id);

        }else {
            throw new ResourceNotFoundException();

        }

    }

    public void updateStockQuantity(Optional<Products> products, SaleDTO saleDTO) {

        products.get().setQuantityInStock(products.get().getQuantityInStock() -  saleDTO.getQuantitySold());

        productsRepo.save(products.get());
    }
}
