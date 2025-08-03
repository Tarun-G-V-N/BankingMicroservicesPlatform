package com.smartbank.loanservice.query.handler;

import com.smartbank.loanservice.dtos.LoansDTO;
import com.smartbank.loanservice.query.FindLoanQuery;
import com.smartbank.loanservice.services.ILoanService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoanQueryHandler {
    private final ILoanService loanService;

    @QueryHandler
    public LoansDTO findLoan(FindLoanQuery findLoanQuery) {
        return loanService.fetchLoan(findLoanQuery.getMobileNumber());
    }
}
