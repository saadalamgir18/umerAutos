package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.repositories.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService{


    @Autowired private SalesRepository salesRepository;


    public double getTodayTotalSalesAmount() {
        Double total = salesRepository.findTodayTotalSalesAmount();
        return total != null ? total : 0.0;
    }

    @Override
    public double getMonthlyRevenue() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime today = LocalDateTime.now();

        Timestamp start = Timestamp.valueOf(startOfMonth);
        Timestamp end = Timestamp.valueOf(today);
        return salesRepository.getMonthlyRevenue(start, end);
    }




    @Override
    public List<SalesResponseDTO> findTodaySales() {
        List<Object[]> rawResults = salesRepository.findTodaySalesSummary();
        return rawResults.stream().map(row-> SalesResponseDTO
                .builder()
                .productId((UUID) row[0])
                .productName((String) row[1])
                .quantitySold(((Number) row[2]).intValue())
                .totalPrice(((Number) row[3]).doubleValue())
                .profit(((Number) row[4]).doubleValue())
                .build()).collect(Collectors.toList());
    }
    @Override
    public PaginatedResponseDTO<SalesResponseDTO> findAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        Page<Object[]> rawResults = salesRepository.findAllSales(pageable);
        PaginationDTO pagination = new PaginationDTO(
                rawResults.getTotalElements(),
                rawResults.getTotalPages(),
                page,
                limit
        );

        System.out.println("fetched today sales");
        List<SalesResponseDTO> responseDTOS =  rawResults.getContent().stream().map(row-> SalesResponseDTO
                .builder()
                .productId((UUID) row[0])
                .productName((String) row[1])
                .quantitySold(((Number) row[2]).intValue())
                .totalPrice(((Number) row[3]).doubleValue())
                .profit(((Number) row[4]).doubleValue())
                .id((UUID) row[5])
                .createdAt((Date) row[6])
                .build()).collect(Collectors.toList());

        return new PaginatedResponseDTO<>(responseDTOS, pagination);

    }

    @Override
    public SalesUpdateResponseDTO findSaleById(UUID id) {
        return salesRepository.findById(id).map(sales -> {
            Products product = sales.getProduct();

            return SalesUpdateResponseDTO.builder()
                    .id(sales.getId())
                    .quantitySold(sales.getQuantitySold())
                    .totalPrice(sales.getTotalAmount())
                    .product(ProductInfoDTO.builder()
                            .id(product.getId())
                            .productName(product.getName())
                            .sku(product.getSku())
                            .build())
                    .build();
        }).orElseThrow(() -> new RuntimeException("Sale not found"));

    }


    // Helper to convert binary UUID to java.util.UUID
    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
