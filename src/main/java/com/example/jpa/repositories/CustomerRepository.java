package com.example.jpa.repositories;

import com.example.jpa.models.Customer;
import com.example.jpa.models.SecurityGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    /**
     * Automatically generated query
     * @param securityGroup The actual parameter
     * @return A list containing all customers with that security group
     */
    List<Customer> findCustomerBySecurityGroupsContaining(SecurityGroup securityGroup);

    List<Customer> findCustomerByNameIs(String name);

    @Query("""
            select c from Customer c inner join c.securityGroups group \s
            where group.action = :action and group.policy = com.example.jpa.models.SecurityPolicy.ALLOWED
            """)
    List<Customer> findCustomerByAllowedSecurityGroupActionName(@Param("action") String actionName);

}
