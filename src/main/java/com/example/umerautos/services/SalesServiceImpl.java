package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.ProductsRepository;
import com.example.umerautos.repositories.SalesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService {


    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ProductsRepository productsRepository;
    private static final Logger logger = LoggerFactory.getLogger(SalesServiceImpl.class);


    public int getTodayTotalSalesAmount() {
        logger.info("Calculating today's total sales amount");
        int amount = salesRepository.findTodayTotalSalesAmount();
        logger.info("Today's total sales amount: {}", amount);
        return amount;
    }

    @Override
    public int getMonthlyRevenue() {
        logger.info("Calculating monthly revenue");
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime today = LocalDateTime.now();

        Timestamp start = Timestamp.valueOf(startOfMonth);
        Timestamp end = Timestamp.valueOf(today);
        Integer revenue = salesRepository.getMonthlyRevenue(start, end);

        int result = revenue != null ? revenue : 0;
        logger.info("Monthly revenue: {}", result);
        return result;
    }

    @Override
    public SalesUpdateResponseDTO updateSale(SaleUpdateRequestDTO requestDTO, Long id) {
        logger.info("Updating sale with id: {}", id);
        Optional<Sales> sales = salesRepository.findById(id);
        if (sales.isPresent()) {
            sales.get().setQuantitySold(requestDTO.quantitySold());
            sales.get().setTotalAmount(requestDTO.totalAmount());

            Sales updatedSales = salesRepository.save(sales.get());
            logger.info("Sale updated successfully");
            return SalesUpdateResponseDTO.mapToDTO(updatedSales);
        }
        logger.warn("Sale not found for update with id: {}", id);
        return null;
    }

    @Override
    public void deleteOne(Long id) {
        logger.info("Deleting sale with id: {}", id);
        try {

            Optional<Sales> sales = salesRepository.findById(id);
            if (sales.isPresent()) {
                Products products = sales.get().getProduct();
                products.setQuantityInStock(products.getQuantityInStock() + sales.get().getQuantitySold());

                productsRepository.save(products);

                salesRepository.deleteById(id);
                logger.info("Sale deleted successfully");
            } else {
                logger.error("Sale not found for deletion with id: {}", id);
                throw new ResourceNotFoundException("sale is not present with id " + id);
            }

        } catch (Exception e) {
            logger.error("Error deleting sale with id: {}", id, e);
            throw new RuntimeException(e);
        }


    }


    @Override
    public List<SalesResponseDTO> findTodaySales(int page, int limit) {
        logger.info("Fetching today's sales. Page: {}, Limit: {}", page, limit);
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Object[]> rawResults = salesRepository.findTodaySalesSummary(pageable);
        logger.info("Found {} sales records for today", rawResults.getContent().size());
        return rawResults.stream().map(row -> SalesResponseDTO
                .builder()
                .productId((Long) row[0])
                .productName((String) row[1])
                .quantitySold(((Number) row[2]).intValue())
                .totalPrice(((Number) row[3]).doubleValue())
                .profit(((Number) row[4]).intValue())
                .build()).collect(Collectors.toList());
    }

    @Override
    public PaginatedResponseDTO<SalesResponseDTO> findAll(int page, int limit) {
        logger.info("Fetching all sales. Page: {}, Limit: {}", page, limit);
        Sort sort = Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page - 1, limit, sort);

        Page<Object[]> rawResults = salesRepository.findAllSales(pageable);
        logger.info("Found {} sales records", rawResults.getTotalElements());
        PaginationDTO pagination = new PaginationDTO(
                rawResults.getTotalElements(),
                rawResults.getTotalPages(),
                page,
                limit
        );

        List<SalesResponseDTO> responseDTOS = rawResults.getContent().stream().map(row -> SalesResponseDTO
                .builder()
                .productId((Long) row[0])
                .productName((String) row[1])
                .quantitySold(((Number) row[2]).intValue())
                .totalPrice(((Number) row[3]).doubleValue())
                .profit(((Number) row[4]).intValue())
                .id((Long) row[5])
                .createdAt((Date) row[6])
                .paymentStatus((PaymentStatus) row[7])
                .build()).collect(Collectors.toList());

        return new PaginatedResponseDTO<>(responseDTOS, pagination);

    }

    @Override
    public SalesUpdateResponseDTO findSaleById(Long id) {
        logger.info("Fetching sale with id: {}", id);
        Optional<Sales> existingSale = salesRepository.findById(id);


        if (existingSale.isPresent()) {
            logger.info("Sale found with id: {}", id);
            Products product = existingSale.get().getProduct();

            return SalesUpdateResponseDTO.builder()
                    .id(existingSale.get().getId())
                    .quantitySold(existingSale.get().getQuantitySold())
                    .totalPrice(existingSale.get().getTotalAmount())
                    .paymentStatus(existingSale.get().getPaymentStatus())
                    .product(ProductInfoDTO.builder()
                            .id(product.getId())
                            .productName(product.getName())
                            .build())
                    .build();

        }
        logger.error("Sale not found with id: {}", id);
        throw new ResourceNotFoundException("sale with this id not exist: " + id);

    }
}
