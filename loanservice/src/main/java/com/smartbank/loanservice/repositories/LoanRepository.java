package com.smartbank.loanservice.repositories;

import com.smartbank.loanservice.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByMobileNumberAndIsActive(String mobileNumber, boolean isActive);

    Optional<Loan> findByLoanNumberAndIsActive(long loanNumber, boolean isActive);
}
