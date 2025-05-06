package com.example.umerautos.services;

import com.example.umerautos.dto.SaleDTO;
import com.example.umerautos.dto.SalesSummaryRequestDTO;
import com.example.umerautos.dto.SalesSummaryResponseDTO;
import com.example.umerautos.entities.Products;
import com.example.umerautos.entities.Sales;
import com.example.umerautos.entities.SalesSummary;
import com.example.umerautos.repositories.ProductsRepository;
import com.example.umerautos.repositories.SalesRepository;
import com.example.umerautos.repositories.SalesSummaryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesSummaryServiceImpl implements SalesSummaryService{
    @Autowired private SalesSummaryRepository salesSummaryRepository;
    @Autowired private ProductsRepository productsRepo;
    @Autowired private SalesRepository salesRepository;


    @Override
    @Transactional
    public SalesSummaryResponseDTO saveOne(SalesSummaryRequestDTO salesSummaryRequestDTO) {

        SalesSummary salesSummary = SalesSummary.builder()
                .customerName(salesSummaryRequestDTO.getCustomerName())
                .paymentStatus(salesSummaryRequestDTO.getPaymentStatus())
                .build();

        double totalAmountSummary = 0;
        int quantitySummary = 0;



        List<Sales> saleItemsList = new ArrayList<>();
        for (SaleDTO saleDTO :salesSummaryRequestDTO.getSaleItems()){
            Optional<Products> products = productsRepo.findById(saleDTO.getProductId());
            if (products.isPresent()){
                System.out.println("product exist! " + saleDTO.getProductId());
                Sales sales = Sales.builder()
                        .product(products.get())
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

        return SalesSummaryResponseDTO.mapToDTO(newSalesSummary);


    }

    @Override
    public List<SalesSummaryResponseDTO> findAll() {
        List<SalesSummary> salesSummaries =  salesSummaryRepository.findAll();
        return salesSummaries.stream().map(salesSummary -> SalesSummaryResponseDTO.mapToDTO(salesSummary)).collect(Collectors.toList());
    }
}
