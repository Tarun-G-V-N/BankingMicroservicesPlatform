package com.smartbank.loanservice.services;

import com.smartbank.loanservice.command.events.LoanUpdatedEvent;
import com.smartbank.loanservice.dtos.LoansDTO;
import com.smartbank.loanservice.entities.Loan;

public interface ILoanService {
    void createLoan(Loan loan);
    LoansDTO fetchLoan(String mobileNumber);
    boolean updateLoan(LoanUpdatedEvent loanUpdatedEvent);
    boolean deleteLoan(Long loanNumber);
    boolean updateMobileNumber(String mobileNumber, String newMobileNumber);
    boolean updateCommunicationStatus(Long loanNumber);
}
