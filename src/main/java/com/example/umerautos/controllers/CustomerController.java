package com.example.umerautos.controllers;

import com.example.umerautos.dto.CustomerRequestDto;
import com.example.umerautos.dto.CustomerResponseDto;
import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponseDto> createCustomer(@RequestBody CustomerRequestDto customerRequestDto) {
        logger.info("Request received to create customer: {}", customerRequestDto.getName());
        try {
            CustomerResponseDto createdCustomer = customerService.create(customerRequestDto);
            logger.info("Customer created successfully with ID: {}", createdCustomer.id());
            return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Error creating customer: {}", customerRequestDto.getName(), e);
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        logger.info("Request received to fetch customer with ID: {}", id);
        try {
            CustomerResponseDto customer = customerService.getById(id);
            logger.info("Customer fetched successfully with ID: {}", id);
            return ResponseEntity.ok(customer);
        } catch (Exception e) {
            logger.error("Error fetching customer with ID: {}", id, e);
            throw e;
        }
    }

    @GetMapping
    public ResponseEntity<PaginatedResponseDTO<CustomerResponseDto>> getAllCustomers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String name) {
        logger.info("Request received to fetch all customers. Page: {}, Size: {}, Name: {}", page, size, name);
        try {
            Pageable pageable = PageRequest.of(page, size);
            PaginatedResponseDTO<CustomerResponseDto> customers = customerService.getAll(pageable, name);
            logger.info("Fetched {} customers", customers.getData().size());
            return ResponseEntity.ok(customers);
        } catch (Exception e) {
            logger.error("Error fetching customers", e);
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDto customerRequestDto) {
        logger.info("Request received to update customer with ID: {}", id);
        try {
            CustomerResponseDto updatedCustomer = customerService.update(id, customerRequestDto);
            logger.info("Customer updated successfully with ID: {}", id);
            return ResponseEntity.ok(updatedCustomer);
        } catch (Exception e) {
            logger.error("Error updating customer with ID: {}", id, e);
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        logger.info("Request received to delete customer with ID: {}", id);
        try {
            customerService.delete(id);
            logger.info("Customer deleted successfully with ID: {}", id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error("Error deleting customer with ID: {}", id, e);
            throw e;
        }
    }
}
