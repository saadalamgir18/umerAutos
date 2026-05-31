package com.example.umerautos.services;

import com.example.umerautos.dto.CustomerResponseDto;
import com.example.umerautos.repositories.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomersServiceImpl implements CustomersService {
    private final CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomersServiceImpl.class);

    public CustomersServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerResponseDto> findAll() {
        logger.info("Fetching all customers (CustomersServiceImpl)");
        List<CustomerResponseDto> customers = customerRepository.findAll().stream()
                .map(CustomerResponseDto::mapToDto)
                .collect(Collectors.toList());
        logger.info("Found {} customers", customers.size());
        return customers;
    }
}
