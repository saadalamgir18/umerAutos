package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.entities.SalesSummary;
import com.example.umerautos.producer.EmailProducer;
import com.example.umerautos.repositories.ProductsRepository;
import com.example.umerautos.repositories.SalesRepository;
import com.example.umerautos.repositories.SalesSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service

public class SalesSummaryServiceImpl implements SalesSummaryService{

    private SalesSummaryRepository salesSummaryRepository;
    private ProductsRepository productsRepo;
    private SalesRepository salesRepository;
    private ProductsService productsService;
    private EmailProducer emailProducer;

    public SalesSummaryServiceImpl(SalesSummaryRepository salesSummaryRepository,
                                   ProductsRepository productsRepo, SalesRepository salesRepository,
                                   ProductsService productsService,
                                   EmailProducer emailProducer) {
        this.salesSummaryRepository = salesSummaryRepository;
        this.productsRepo = productsRepo;
        this.salesRepository = salesRepository;
        this.productsService = productsService;
        this.emailProducer = emailProducer;
    }

    @Value(value = "${lowItemThreshold}")
    private int lowItemThreshold ;


    @Override
    @Transactional
    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO) {



        Map<String, Integer> lowStockItems = new HashMap<>();

        SalesSummary salesSummary = SalesSummary.builder()
                .customerName(salesSummaryRequestDTO.getCustomerName())
                .paymentStatus(salesSummaryRequestDTO.getPaymentStatus())
                .build();

        double totalAmountSummary = 0;
        int quantitySummary = 0;



        List<Sales> saleItemsList = new ArrayList<>();

        for (SaleDTO saleDTO :salesSummaryRequestDTO.getSaleItems()){
            Optional<Products> product = productsRepo.findById(saleDTO.getProductId());
            if (product.isPresent()){


                if (product.get().getQuantityInStock() < lowItemThreshold){
                    lowStockItems.put(product.get().getName(), product.get().getQuantityInStock());
                }

                productsService.updateStockQuantity(product, saleDTO);

                Sales sales = Sales.builder()
                        .product(product.get())
                        .quantitySold(saleDTO.getQuantitySold())
                        .totalAmount(saleDTO.getTotalAmount())
                        .salesSummary(salesSummary)
                        .build();

                totalAmountSummary += saleDTO.getTotalAmount();
                quantitySummary += saleDTO.getQuantitySold();

                saleItemsList.add(sales);


            }

        }

        salesSummary.setQuantitySold(quantitySummary);
        salesSummary.setTotalAmount(totalAmountSummary);
        salesSummary.setSaleItems(saleItemsList);

        SalesSummary newSalesSummary = salesSummaryRepository.save(salesSummary);

        if (!lowStockItems.isEmpty()){
            System.out.println("items are low in stock");

            emailProducer.sendMessage("low-stock-alerts", LowStockEmailDTO.builder()
                            .lowStock(lowStockItems)
                    .build());


        }

        return SalesSummaryResponseDTO.mapToDTO(newSalesSummary);


    }



    @Override
    public PaginatedResponseDTO<SalesSummaryResponseDTO> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);


        Page<SalesSummary> salesSummaries =  salesSummaryRepository.findAll(pageable);
        PaginationDTO pagination = new PaginationDTO(
                salesSummaries.getTotalElements(),
                salesSummaries.getTotalPages(),
                page,
                limit
        );
        List<SalesSummaryResponseDTO> salesSummaryResponseDTOS =  salesSummaries.stream().map(salesSummary -> SalesSummaryResponseDTO.mapToDTO(salesSummary)).collect(Collectors.toList());
        return new PaginatedResponseDTO<>(salesSummaryResponseDTOS, pagination);
    }
}
