package com.example.jpa;

import com.example.jpa.models.CustomerTestFactory;
import com.example.jpa.models.SecurityGroupTestFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Random;

@TestConfiguration(proxyBeanMethods = false)
public class IntegrationTestConfiguration {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }

    @Bean
    @ConditionalOnMissingBean(Random.class)
    Random random() {
        return new Random();
    }

    @Bean
    @ConditionalOnMissingBean(CustomerTestFactory.class)
    CustomerTestFactory customerTestFactory(SecurityGroupTestFactory groupTestFactory, Random random) {
        return new CustomerTestFactory(groupTestFactory, random);
    }

    @Bean
    @ConditionalOnMissingBean(SecurityGroupTestFactory.class)
    SecurityGroupTestFactory securityGroupTestFactory(Random random) {
        return new SecurityGroupTestFactory(random);
    }

    public static void main(String[] args) {
        SpringApplication.from(WorkshopSpringBootDataJpaApplication::main).with(IntegrationTestConfiguration.class).run(args);
    }

}
