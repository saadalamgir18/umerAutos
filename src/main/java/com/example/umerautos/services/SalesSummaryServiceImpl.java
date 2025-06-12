package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.entities.SalesSummary;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.producer.EmailProducer;
import com.example.umerautos.repositories.ProductsRepository;
import com.example.umerautos.repositories.SalesRepository;
import com.example.umerautos.repositories.SalesSummaryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SalesSummaryServiceImpl implements SalesSummaryService {

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
    private int lowItemThreshold;


    @Override
    @Transactional
    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO) {


        Map<String, Integer> lowStockItems = new HashMap<>();
        int totalAmountSummary = 0;
        int quantitySummary = 0;

        SalesSummary salesSummary = SalesSummary.builder()
                .customerName(salesSummaryRequestDTO.customerName())
                .paymentStatus(salesSummaryRequestDTO.paymentStatus())
                .build();


        List<Sales> saleItemsList = new ArrayList<>();

        for (SaleDTO saleDTO : salesSummaryRequestDTO.saleItems()) {
            Optional<Products> product = productsRepo.findById(saleDTO.productId());
            if (product.isPresent()) {

                if (product.get().getQuantityInStock() < lowItemThreshold) {
                    lowStockItems.put(product.get().getName(), product.get().getQuantityInStock());
                }

                productsService.updateStockQuantity(product, saleDTO);

                Sales sales = Sales.builder()
                        .product(product.get())
                        .quantitySold(saleDTO.quantitySold())
                        .totalAmount(saleDTO.totalAmount())
                        .salesSummary(salesSummary)
                        .paymentStatus(salesSummaryRequestDTO.paymentStatus())
                        .build();

                totalAmountSummary += saleDTO.totalAmount();
                quantitySummary += saleDTO.quantitySold();

                saleItemsList.add(sales);


            }

        }

        salesSummary.setQuantitySold(quantitySummary);
        salesSummary.setTotalAmount(totalAmountSummary);
        salesSummary.setSaleItems(saleItemsList);

        SalesSummary newSalesSummary = salesSummaryRepository.save(salesSummary);

        if (!lowStockItems.isEmpty()) {

            this.sendEmail(lowStockItems);

        }

        return SalesSummaryResponseDTO.mapToDTO(newSalesSummary);


    }

    void sendEmail(Map<String, Integer> lowStockItems) {
        emailProducer.sendMessage("low-stock-alerts", LowStockEmailDTO.builder()
                .lowStock(lowStockItems)
                .build());

    }


    @Override
    public PaginatedResponseDTO<SalesSummaryResponseDTO> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);


        Page<SalesSummary> salesSummaries = salesSummaryRepository.findAll(pageable);
        PaginationDTO pagination = new PaginationDTO(
                salesSummaries.getTotalElements(),
                salesSummaries.getTotalPages(),
                page,
                limit
        );
        List<SalesSummaryResponseDTO> salesSummaryResponseDTOS = salesSummaries.stream().map(salesSummary -> SalesSummaryResponseDTO.mapToDTO(salesSummary)).collect(Collectors.toList());
        return new PaginatedResponseDTO<>(salesSummaryResponseDTOS, pagination);
    }

    @Override
    public PaginatedResponseDTO<SalesSummaryResponseDTO> findSalesSummary(int page, int limit, PaymentStatus paymentStatus) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SalesSummary> salesSummaries = salesSummaryRepository.findByPaymentStatus(paymentStatus, pageable);

        PaginationDTO pagination = new PaginationDTO(
                salesSummaries.getTotalElements(),
                salesSummaries.getTotalPages(),
                page,
                limit
        );
        List<SalesSummaryResponseDTO> salesSummaryResponseDTOS = salesSummaries
                .stream()
                .map(salesSummary -> SalesSummaryResponseDTO.mapToDTO(salesSummary))
                .collect(Collectors.toList());


        return new PaginatedResponseDTO<>(salesSummaryResponseDTOS, pagination);
    }

    @Override
    public SalesSummaryResponseDTO findSalesSummaryById(UUID id) {
        return salesSummaryRepository.findById(id)
                .map(SalesSummaryResponseDTO::mapToDTO).orElse(null);
    }


    @Override
    @Transactional
    public SalesSummaryResponseDTO updateSaleSummaryById(UUID id, SalesSummaryUpdate request) {

        Optional<SalesSummary> existingSalesSummary = salesSummaryRepository.findById(id);
        if (existingSalesSummary.isEmpty()) {
            throw new ResourceNotFoundException("sales summary with id: " + id + " does not exist!");
        }

        Optional<Sales> existingSingleSale = salesRepository.findById(request.saleItemId());
        if (existingSingleSale.isEmpty()) {
            throw new ResourceNotFoundException("sales with id: " + request.saleItemId() + " does not exist!");
        }

        existingSingleSale.get().setPaymentStatus(PaymentStatus.PAID);
//
        existingSalesSummary.get().setQuantitySold(existingSalesSummary.get().getQuantitySold() - existingSingleSale.get().getQuantitySold());
        existingSalesSummary.get().setTotalAmount(existingSalesSummary.get().getTotalAmount() - existingSingleSale.get().getTotalAmount());

//        existingSalesSummary.get().setSaleItems(
//                existingSalesSummary
//                        .get()
//                        .getSaleItems().stream()
//                        .filter(saleItem -> !saleItem.getId().equals(request.saleItemId())).toList()
//        );

//        existingSalesSummary.get().getSaleItems().forEach(saleItem -> System.out.println(saleItem.getId()));
        salesRepository.save(existingSingleSale.get());

        SalesSummary salesSummary = salesSummaryRepository.save(existingSalesSummary.get());
        return SalesSummaryResponseDTO.mapToDTO(salesSummary);
        
    }

}
