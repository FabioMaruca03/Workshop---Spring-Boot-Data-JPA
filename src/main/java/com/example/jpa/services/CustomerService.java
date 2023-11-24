package com.example.jpa.services;

import com.example.jpa.models.Customer;
import com.example.jpa.models.SecurityGroup;
import com.example.jpa.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer save(Customer customer) {
        log.debug("Persisting {}", customer);
        return customerRepository.save(customer);
    }

    public Optional<Customer> findCustomerById(UUID id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAllCustomerWithSecurityGroup(SecurityGroup group) {
        return customerRepository.findCustomerBySecurityGroupsContaining(group);
    }

    public List<Customer> findAllCustomersAllowedByAction(String action) {
        return customerRepository.findCustomerByAllowedSecurityGroupActionName(action);
    }

    public List<Customer> findAllCustomers() {
        return customerRepository.findAll();
    }

    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }

}
