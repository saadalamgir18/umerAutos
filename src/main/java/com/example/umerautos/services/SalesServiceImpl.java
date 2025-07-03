package com.example.umerautos.services;

import com.example.umerautos.dto.*;
import com.example.umerautos.entities.PaymentStatus;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.ProductsRepository;
import com.example.umerautos.repositories.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
public class SalesServiceImpl implements SalesService {


    @Autowired
    private SalesRepository salesRepository;
    @Autowired
    private ProductsRepository productsRepository;


    public int getTodayTotalSalesAmount() {
        return salesRepository.findTodayTotalSalesAmount();
    }

    @Override
    public int getMonthlyRevenue() {
        LocalDateTime startOfMonth = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime today = LocalDateTime.now();

        Timestamp start = Timestamp.valueOf(startOfMonth);
        Timestamp end = Timestamp.valueOf(today);
        Integer revenue = salesRepository.getMonthlyRevenue(start, end);

        return revenue != null ? revenue : 0;
    }

    @Override
    public SalesUpdateResponseDTO updateSale(SaleUpdateRequestDTO requestDTO, UUID id) {
        Optional<Sales> sales = salesRepository.findById(id);
        if (sales.isPresent()) {
            sales.get().setQuantitySold(requestDTO.quantitySold());
            sales.get().setTotalAmount(requestDTO.totalAmount());

            Sales updatedSales = salesRepository.save(sales.get());
            return SalesUpdateResponseDTO.mapToDTO(updatedSales);
        }
        return null;
    }

    @Override
    public void deleteOne(UUID id) {

        try {

            Optional<Sales> sales = salesRepository.findById(id);
            if (sales.isPresent()) {
                Products products = sales.get().getProduct();
                products.setQuantityInStock(products.getQuantityInStock() + sales.get().getQuantitySold());

                productsRepository.save(products);

                salesRepository.deleteById(id);
            } else {

                throw new ResourceNotFoundException("sale is not present with id " + id);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }


    @Override
    public List<SalesResponseDTO> findTodaySales(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);

        List<Object[]> rawResults = salesRepository.findTodaySalesSummary(pageable);
        System.out.println("rawResults: " + rawResults);
        return rawResults.stream().map(row -> SalesResponseDTO
                .builder()
                .productId((UUID) row[0])
                .productName((String) row[1])
                .quantitySold(((Number) row[2]).intValue())
                .totalPrice(((Number) row[3]).intValue())
                .profit(((Number) row[4]).intValue())
                .build()).collect(Collectors.toList());
    }

    @Override
    public PaginatedResponseDTO<SalesResponseDTO> findAll(int page, int limit) {
        Sort sort = Sort.by("createdAt").descending();

        Pageable pageable = PageRequest.of(page, limit, sort);

        Page<Object[]> rawResults = salesRepository.findAllSales(pageable);
        PaginationDTO pagination = new PaginationDTO(
                rawResults.getTotalElements(),
                rawResults.getTotalPages(),
                page,
                limit
        );

        List<SalesResponseDTO> responseDTOS = rawResults.getContent().stream().map(row -> SalesResponseDTO
                .builder()
                .productId((UUID) row[0])
                .productName((String) row[1])
                .quantitySold(((Number) row[2]).intValue())
                .totalPrice(((Number) row[3]).intValue())
                .profit(((Number) row[4]).intValue())
                .id((UUID) row[5])
                .createdAt((Date) row[6])
                .paymentStatus((PaymentStatus) row[7])
                .build()).collect(Collectors.toList());

        return new PaginatedResponseDTO<>(responseDTOS, pagination);

    }

    @Override
    public SalesUpdateResponseDTO findSaleById(UUID id) {

        Optional<Sales> existingSale = salesRepository.findById(id);


        System.out.println("existing sale is: " + existingSale.get().getId());

        if (existingSale.isPresent()) {

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
        throw new ResourceNotFoundException("sale with this id not exist: " + id);

    }


    // Helper to convert binary UUID to java.util.UUID
    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
