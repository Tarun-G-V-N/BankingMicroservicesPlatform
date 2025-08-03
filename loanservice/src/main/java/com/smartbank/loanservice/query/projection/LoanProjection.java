package com.smartbank.loanservice.query.projection;

import com.smartbank.common.events.LoanMobileNumberRollbackedEvent;
import com.smartbank.common.events.LoanMobileNumberUpdatedEvent;
import com.smartbank.loanservice.command.events.LoanCreatedEvent;
import com.smartbank.loanservice.command.events.LoanDeletedEvent;
import com.smartbank.loanservice.command.events.LoanUpdatedEvent;
import com.smartbank.loanservice.entities.Loan;
import com.smartbank.loanservice.services.ILoanService;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;



@Component
@RequiredArgsConstructor
@ProcessingGroup("loan-group")
public class LoanProjection {
    private final ILoanService loanService;

    @EventHandler
    public void on(LoanCreatedEvent loanCreatedEvent) {
        Loan loan = new Loan();
        BeanUtils.copyProperties(loanCreatedEvent, loan);
        loanService.createLoan(loan);
    }

    @EventHandler
    public void on(LoanUpdatedEvent loanUpdatedEvent) {
        loanService.updateLoan(loanUpdatedEvent);
    }

    @EventHandler
    public void on(LoanDeletedEvent loanDeletedEvent) {
        loanService.deleteLoan(loanDeletedEvent.getLoanNumber());
    }

    @EventHandler
    public void on(LoanMobileNumberUpdatedEvent loanMobileNumberUpdatedEvent) {
        loanService.updateMobileNumber(loanMobileNumberUpdatedEvent.getMobileNumber(), loanMobileNumberUpdatedEvent.getNewMobileNumber());
    }

    @EventHandler
    public void on(LoanMobileNumberRollbackedEvent loanMobileNumberRollbackedEvent) {
        loanService.updateMobileNumber(loanMobileNumberRollbackedEvent.getNewMobileNumber(), loanMobileNumberRollbackedEvent.getMobileNumber());
    }
}
