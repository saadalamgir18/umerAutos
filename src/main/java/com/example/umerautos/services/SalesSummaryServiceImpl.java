package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.entities.SalesSummary;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.ProductsRepository;
import com.example.umerautos.repositories.SalesRepository;
import com.example.umerautos.repositories.SalesSummaryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SalesSummaryServiceImpl implements SalesSummaryService {

    private final SalesSummaryRepository salesSummaryRepository;
    private final ProductsRepository productsRepo;
    private final SalesRepository salesRepository;
    private final ProductsService productsService;

    public SalesSummaryServiceImpl(SalesSummaryRepository salesSummaryRepository,
                                   ProductsRepository productsRepo, SalesRepository salesRepository,
                                   ProductsService productsService) {
        this.salesSummaryRepository = salesSummaryRepository;
        this.productsRepo = productsRepo;
        this.salesRepository = salesRepository;
        this.productsService = productsService;
    }


    @Override
    @Transactional
    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO) {


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


        return SalesSummaryResponseDTO.mapToDTO(newSalesSummary);


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
        List<SalesSummaryResponseDTO> salesSummaryResponseDTOS = salesSummaries.stream().map(SalesSummaryResponseDTO::mapToDTO).collect(Collectors.toList());
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

        existingSalesSummary.get().setQuantitySold(existingSalesSummary.get().getQuantitySold() - existingSingleSale.get().getQuantitySold());
        existingSalesSummary.get().setTotalAmount(existingSalesSummary.get().getTotalAmount() - existingSingleSale.get().getTotalAmount());

        salesRepository.save(existingSingleSale.get());

        SalesSummary salesSummary = salesSummaryRepository.save(existingSalesSummary.get());
        return SalesSummaryResponseDTO.mapToDTO(salesSummary);

    }

    @Override
    @Transactional
    public SalesSummaryResponseDTO updateSaleSummaryById(UUID id) {
        Optional<SalesSummary> existingSalesSummary = salesSummaryRepository.findById(id);
        if (existingSalesSummary.isEmpty()) {
            throw new ResourceNotFoundException("sales summary with id: " + id + " does not exist!");
        }

        existingSalesSummary.get().setPaymentStatus(PaymentStatus.PAID);

        existingSalesSummary.get().setQuantitySold(0);
        existingSalesSummary.get().setTotalAmount(0);

        SalesSummary summary = existingSalesSummary.get();

        for (Sales sale : summary.getSaleItems()) {
            sale.setPaymentStatus(PaymentStatus.PAID);
        }

        SalesSummary salesSummary = salesSummaryRepository.save(summary);

        return SalesSummaryResponseDTO.mapToDTO(salesSummary);
    }

}
