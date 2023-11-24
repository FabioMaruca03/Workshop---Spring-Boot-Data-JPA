package com.example.jpa.services;

import com.example.jpa.IntegrationTestConfiguration;
import com.example.jpa.models.Customer;
import com.example.jpa.models.CustomerTestFactory;
import com.example.jpa.models.SecurityGroup;
import com.example.jpa.models.SecurityPolicy;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Import(IntegrationTestConfiguration.class)
@SpringBootTest(properties = "logging.level.com.example.jpa.services.CustomerService=TRACE")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerTestFactory customerTestFactory;

    @Test
    void shouldFindAllCustomers() {
        final var customer = customerTestFactory.generateRandomCustomer();

        customerService.save(customer);

        List<Customer> customers = customerService.findAllCustomers();

        assertFalse(customers.isEmpty());
        assertEquals(customer.getId(), customers.get(0).getId());
    }

    @Test
    void shouldFindCustomersByActivePolicies() {
        final var customer = customerTestFactory.generateRandomCustomer();

        customerService.save(customer);

        Optional<SecurityGroup> testGroup = customer.getSecurityGroups().stream()
                .filter(x -> x.getPolicy().equals(SecurityPolicy.ALLOWED))
                .findFirst();

        assertTrue(testGroup.isPresent());

        List<Customer> customers = customerService.findAllCustomerWithSecurityGroup(testGroup.get());

        List<Customer> allCustomersAllowedByAction = customerService.findAllCustomersAllowedByAction(testGroup.get().getAction());

        assertEquals(customers.get(0).getId(), allCustomersAllowedByAction.get(0).getId());
    }

    @Test
    void shouldNotFindCustomersByActivePolicies() {
        final var customer = customerTestFactory.generateRandomCustomer();

        customerService.save(customer);

        Optional<SecurityGroup> testGroup = customer.getSecurityGroups().stream()
                .filter(x -> x.getPolicy().equals(SecurityPolicy.NOT_ALLOWED))
                .findFirst();

        assertTrue(testGroup.isPresent());

        List<Customer> customers = customerService.findAllCustomerWithSecurityGroup(testGroup.get());

        assertEquals(1, customers.size());

        List<Customer> allCustomersAllowedByAction = customerService.findAllCustomersAllowedByAction(testGroup.get().getAction());

        assertTrue(allCustomersAllowedByAction.isEmpty());
    }

}
