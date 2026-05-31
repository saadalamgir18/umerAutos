package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.*;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.CustomerRepository;
import com.example.umerautos.repositories.ProductsRepository;
import com.example.umerautos.repositories.SalesRepository;
import com.example.umerautos.repositories.SalesSummaryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesSummaryServiceImpl implements SalesSummaryService {

    private final SalesSummaryRepository salesSummaryRepository;
    private final ProductsRepository productsRepo;
    private final SalesRepository salesRepository;
    private final ProductsService productsService;
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(SalesSummaryServiceImpl.class);

    public SalesSummaryServiceImpl(SalesSummaryRepository salesSummaryRepository,
                                   ProductsRepository productsRepo, SalesRepository salesRepository,
                                   ProductsService productsService, CustomerRepository customerRepository) {
        this.salesSummaryRepository = salesSummaryRepository;
        this.productsRepo = productsRepo;
        this.salesRepository = salesRepository;
        this.productsService = productsService;
        this.customerRepository = customerRepository;
    }


    @Override
    @Transactional
    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO) {
        logger.info("Saving sales summary");
        Customer customer = null;
        if (salesSummaryRequestDTO.getCustomerId() != null) {
            customer = customerRepository.findById(salesSummaryRequestDTO.getCustomerId())
                    .orElseThrow(() -> {
                        logger.error("Customer not found with id: {}", salesSummaryRequestDTO.getCustomerId());
                        return new ResourceNotFoundException("Customer not found with id: " + salesSummaryRequestDTO.getCustomerId());
                    });
        }

        double totalAmountSummary = 0;
        int quantitySummary = 0;

        SalesSummary salesSummary = SalesSummary.builder()
                .customer(customer)
                .paymentStatus(salesSummaryRequestDTO.getPaymentStatus())
                .build();


        List<Sales> saleItemsList = new ArrayList<>();

        for (SalesSummaryRequestDTO.SaleItemDTO saleDTO : salesSummaryRequestDTO.getSaleItems()) {

            Sales sales = getNewSale(saleDTO.getProductId(), saleDTO.getQuantitySold(), saleDTO.getTotalAmount(), salesSummary, salesSummaryRequestDTO.getPaymentStatus());
            totalAmountSummary += saleDTO.getTotalAmount();
            quantitySummary += saleDTO.getQuantitySold();

            saleItemsList.add(sales);
        }


        salesSummary.setQuantitySold(quantitySummary);
        salesSummary.setTotalAmount(totalAmountSummary);
        salesSummary.setSaleItems(saleItemsList);

        SalesSummary newSalesSummary = salesSummaryRepository.save(salesSummary);
        logger.info("Sales summary saved with id: {}", newSalesSummary.getId());

        return SalesSummaryResponseDTO.mapToDTO(newSalesSummary);


    }


    @Override
    public PaginatedResponseDTO<SalesSummaryResponseDTO> findAll(int page, int limit) {
        logger.info("Fetching all sales summaries. Page: {}, Limit: {}", page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);


        Page<SalesSummary> salesSummaries = salesSummaryRepository.findAll(pageable);
        logger.info("Found {} sales summaries", salesSummaries.getTotalElements());
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
    public PaginatedResponseDTO<SalesSummaryResponseDTO> findSalesSummary(int page, int limit, int customerId, PaymentStatus paymentStatus) {
        logger.info("Fetching sales summaries. Page: {}, Limit: {}, CustomerId: {}, PaymentStatus: {}", page, limit, customerId, paymentStatus);
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SalesSummary> salesSummaries;
        if (customerId > 0) {
            salesSummaries = salesSummaryRepository.findByCustomer_IdAndSaleItems_PaymentStatus((long) customerId, paymentStatus, pageable);
        } else {
            salesSummaries = salesSummaryRepository.findBySaleItems_PaymentStatus(paymentStatus, pageable);
        }
        logger.info("Found {} sales summaries", salesSummaries.getTotalElements());

        PaginationDTO pagination = new PaginationDTO(
                salesSummaries.getTotalElements(),
                salesSummaries.getTotalPages(),
                page,
                limit
        );
        List<SalesSummaryResponseDTO> salesSummaryResponseDTOS = salesSummaries
                .stream()
                .map(SalesSummaryResponseDTO::mapToDTO)
                .collect(Collectors.toList());


        return new PaginatedResponseDTO<>(salesSummaryResponseDTOS, pagination);
    }

    @Override
    public SalesSummaryResponseDTO findSalesSummaryById(Long id) {
        logger.info("Fetching sales summary with id: {}", id);
        return salesSummaryRepository.findById(id)
                .map(SalesSummaryResponseDTO::mapToDTO).orElse(null);
    }


    @Override
    @Transactional
    public String updateSaleById(Long id) {
        logger.info("Updating sale with id: {}", id);
//        SalesSummary existingSalesSummary = this.getSaleSummaryById(id);


        Optional<Sales> existingSingleSale = salesRepository.findById(id);
        if (existingSingleSale.isEmpty()) {
            logger.error("Sales not found with id: {}", id);
            throw new ResourceNotFoundException("sales with id: " + id + " does not exist!");
        }

        existingSingleSale.get().setPaymentStatus(PaymentStatus.PAID);

//        existingSalesSummary.setQuantitySold(existingSalesSummary.getQuantitySold() - existingSingleSale.get().getQuantitySold());
//        existingSalesSummary.setTotalAmount(existingSalesSummary.getTotalAmount() - existingSingleSale.get().getTotalAmount());

        salesRepository.save(existingSingleSale.get());
        logger.info("Sale updated successfully");

//        SalesSummary salesSummary = salesSummaryRepository.save(existingSalesSummary);
        return "Updated";

    }

    @Override
    @Transactional
    public SalesSummaryResponseDTO updateSaleSummaryById(Long id) {
        logger.info("Updating sales summary with id: {}", id);
        SalesSummary existingSalesSummary = this.getSaleSummaryById(id);

        existingSalesSummary.setPaymentStatus(PaymentStatus.PAID);

        existingSalesSummary.setQuantitySold(0);
        existingSalesSummary.setTotalAmount(0);


        for (Sales sale : existingSalesSummary.getSaleItems()) {
            sale.setPaymentStatus(PaymentStatus.PAID);
        }

        SalesSummary salesSummary = salesSummaryRepository.save(existingSalesSummary);
        logger.info("Sales summary updated successfully");

        return SalesSummaryResponseDTO.mapToDTO(salesSummary);
    }

    @Override
    @Transactional
    public SalesSummaryResponseDTO updateSaleSummaryById(Long id, UpdateDebtorsSales request) {
        logger.info("Updating sales summary with id: {}", id);

        SalesSummary existingSalesSummary = this.getSaleSummaryById(id);


        request.saleItems().forEach(newSale -> {
            existingSalesSummary.setTotalAmount(existingSalesSummary.getTotalAmount() + newSale.totalAmount());
            existingSalesSummary.setQuantitySold(existingSalesSummary.getQuantitySold() + newSale.quantitySold());

            Sales sales = getNewSale(newSale.productId(), newSale.quantitySold(), newSale.totalAmount(), existingSalesSummary, PaymentStatus.UNPAID);


            existingSalesSummary.getSaleItems().add(sales);


        });

        SalesSummary salesSummary = salesSummaryRepository.save(existingSalesSummary);
        logger.info("Sales summary updated successfully");
        return SalesSummaryResponseDTO.mapToDTO(salesSummary);


    }

    private SalesSummary getSaleSummaryById(Long id) {

        Optional<SalesSummary> existingSalesSummary = salesSummaryRepository.findById(id);

        if (existingSalesSummary.isEmpty()) {
            logger.error("Sales summary not found with id: {}", id);
            throw new ResourceNotFoundException("sales summary with id: " + id + " does not exist!");
        }
        return existingSalesSummary.get();
    }

    private Sales getNewSale(Long productId, int quantitySold, double totalAmount, SalesSummary salesSummary, PaymentStatus paymentStatus) {

        Optional<Products> product = productsRepo.findById(productId);
        if (product.isPresent()) {

            SaleDTO saleDTO = SaleDTO.builder()
                    .productId(productId)
                    .quantitySold(quantitySold)
                    .totalAmount((int) totalAmount)
                    .build();
            productsService.updateStockQuantity(product, saleDTO);

            Sales sales = Sales.builder()
                    .product(product.get())
                    .quantitySold(quantitySold)
                    .totalAmount(totalAmount)
                    .salesSummary(salesSummary)
                    .paymentStatus(paymentStatus)
                    .build();

            return sales;


        }
        logger.error("Product not found with id: {}", productId);
        throw new ResourceNotFoundException("product does not exist with this id +" + productId);

    }
}
