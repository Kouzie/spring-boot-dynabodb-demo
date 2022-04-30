package com.demo.dynamodb.model.service;


import com.demo.dynamodb.model.Customer;
import com.demo.dynamodb.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repository;

    public Optional<Customer> findById(String id) {
        return repository.findById(id);
    }

    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    public void delete(Customer customer) {
        repository.delete(customer);
    }
}