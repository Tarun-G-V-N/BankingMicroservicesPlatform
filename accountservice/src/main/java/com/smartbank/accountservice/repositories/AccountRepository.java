package com.smartbank.accountservice.repositories;

import com.smartbank.accountservice.entities.Account;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByMobileNumberAndIsActive(String mobileNumber, boolean isActive);
    Optional<Account> findByAccountNumberAndIsActive(Long accountNumber, boolean isActive);
//    @Transactional
//    @Modifying
//    void deleteByAccountNumber(Long accountNumber);
}
