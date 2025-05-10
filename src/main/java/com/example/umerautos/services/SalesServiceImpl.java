package com.example.umerautos.services;

import com.example.umerautos.dto.SalesResponseDTO;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.repositories.SalesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SalesServiceImpl implements SalesService{


    @Autowired private SalesRepository salesRepository;


    public Double getTodayTotalSalesAmount() {
        Double total = salesRepository.findTodayTotalSalesAmount();
        return total != null ? total : 0.0;
    }

//    salesRepo.findSalesSummaryByDate(LocalDate.now().minusDays(1));



    @Override
    public List<SalesResponseDTO> findAll() {
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


    // Helper to convert binary UUID to java.util.UUID
    private UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
