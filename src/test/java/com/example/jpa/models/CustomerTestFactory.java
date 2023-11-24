package com.example.jpa.models;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.context.annotation.Import;

import java.util.Random;

@TestComponent
@RequiredArgsConstructor
@Import({FactoriesConfigs.class, SecurityGroupTestFactory.class})
public class CustomerTestFactory {
    private final SecurityGroupTestFactory securityGroupTestFactory;
    private final Random random;

    public Customer generateRandomCustomer() {
        return Customer.builder()
                .name("User #%s".formatted(random.nextInt(10)))
                .securityGroups(securityGroupTestFactory.generateRandomSecurityGroups(random.nextInt(50)))
                .build();
    }

}
