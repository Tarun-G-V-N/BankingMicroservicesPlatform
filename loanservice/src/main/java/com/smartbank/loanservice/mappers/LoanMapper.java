package com.smartbank.loanservice.mappers;

import com.smartbank.loanservice.command.events.LoanUpdatedEvent;
import com.smartbank.loanservice.dtos.LoansDTO;
import com.smartbank.loanservice.entities.Loan;

public class LoanMapper {
    public static LoansDTO mapToLoansDto(Loan loan, LoansDTO loansDTO) {
        loansDTO.setLoanNumber(loan.getLoanNumber());
        loansDTO.setLoanType(loan.getLoanType());
        loansDTO.setMobileNumber(loan.getMobileNumber());
        loansDTO.setTotalLoan(loan.getTotalLoan());
        loansDTO.setAmountPaid(loan.getAmountPaid());
        loansDTO.setOutstandingAmount(loan.getOutstandingAmount());
        loansDTO.setActive(loan.isActive());
        return loansDTO;
    }

    public static Loan mapToLoans(LoansDTO loansDto, Loan loan) {
        loan.setLoanNumber(loansDto.getLoanNumber());
        loan.setLoanType(loansDto.getLoanType());
        loan.setMobileNumber(loansDto.getMobileNumber());
        loan.setTotalLoan(loansDto.getTotalLoan());
        loan.setAmountPaid(loansDto.getAmountPaid());
        loan.setOutstandingAmount(loansDto.getOutstandingAmount());
        return loan;
    }

    public static Loan mapEventToLoan(LoanUpdatedEvent loanUpdatedEvent, Loan loan) {
        loan.setLoanType(loanUpdatedEvent.getLoanType());
        loan.setTotalLoan(loanUpdatedEvent.getTotalLoan());
        loan.setAmountPaid(loanUpdatedEvent.getAmountPaid());
        loan.setOutstandingAmount(loanUpdatedEvent.getOutstandingAmount());
        return loan;
    }
}
