package com.example.umerautos.services;

import com.example.umerautos.dto.CustomerRequestDto;
import com.example.umerautos.dto.CustomerResponseDto;
import com.example.umerautos.dto.PaginatedResponseDTO;
import com.example.umerautos.dto.PaginationDTO;
import com.example.umerautos.entities.Customer;
import com.example.umerautos.globalException.ResourceNotFoundException;
import com.example.umerautos.repositories.CustomerRepository;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

    @Override
    public CustomerResponseDto create(CustomerRequestDto customerRequestDto) {
        logger.info("Creating new customer: {}", customerRequestDto.getName());
        Customer customer = new Customer();
        customer.setName(customerRequestDto.getName());
        customer.setIdCardNo(customerRequestDto.getIdCardNo());
        customer = customerRepository.save(customer);
        logger.info("Customer created with id: {}", customer.getId());
        return CustomerResponseDto.mapToDto(customer);
    }

    @Override
    public CustomerResponseDto getById(Long id) {
        logger.info("Fetching customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Customer not found with id: {}", id);
                    return new ResourceNotFoundException("Customer not found with id: " + id);
                });
        return CustomerResponseDto.mapToDto(customer);
    }

    @Override
    public PaginatedResponseDTO<CustomerResponseDto> getAll(Pageable pageable, String name) {
        logger.info("Fetching all customers with name filter: {}", name);
        Specification<Customer> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Customer> customerPage = customerRepository.findAll(spec, pageable);
        logger.info("Found {} customers", customerPage.getTotalElements());
        List<CustomerResponseDto> customerResponseDtos = customerPage.getContent().stream()
                .map(CustomerResponseDto::mapToDto)
                .toList();

        PaginationDTO paginationDTO = PaginationDTO.builder()
                .totalItems(customerPage.getTotalElements())
                .totalPages(customerPage.getTotalPages())
                .currentPage(customerPage.getNumber())
                .itemsPerPage(customerPage.getSize())
                .build();

        return new PaginatedResponseDTO<>(
                customerResponseDtos,
                paginationDTO
        );
    }

    @Override
    public CustomerResponseDto update(Long id, CustomerRequestDto customerRequestDto) {
        logger.info("Updating customer with id: {}", id);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Customer not found for update with id: {}", id);
                    return new ResourceNotFoundException("Customer not found with id: " + id);
                });
        customer.setName(customerRequestDto.getName());
        customer.setIdCardNo(customerRequestDto.getIdCardNo());
        customer = customerRepository.save(customer);
        logger.info("Customer updated successfully");
        return CustomerResponseDto.mapToDto(customer);
    }

    @Override
    public void delete(Long id) {
        logger.info("Deleting customer with id: {}", id);
        if (!customerRepository.existsById(id)) {
            logger.error("Customer not found for deletion with id: {}", id);
            throw new ResourceNotFoundException("Customer not found with id: " + id);
        }
        customerRepository.deleteById(id);
        logger.info("Customer deleted successfully");
    }
}
