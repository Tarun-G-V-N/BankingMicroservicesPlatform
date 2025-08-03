package com.smartbank.customerservice.repositories;

import com.smartbank.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByMobileNumberAndIsActive(String mobileNumber, boolean isActive);
    Optional<Customer> findByCustomerIdAndIsActive(String customerId, boolean isActive);
}
