package com.smartbank.loanservice.command.aggregate;

import com.smartbank.common.commands.RollbackCustomerMobileNumberCommand;
import com.smartbank.common.commands.RollbackLoanMobileNumberCommand;
import com.smartbank.common.commands.UpdateLoanMobileNumberCommand;
import com.smartbank.common.events.CustomerMobileNumberRollbackedEvent;
import com.smartbank.common.events.LoanDataChangedEvent;
import com.smartbank.common.events.LoanMobileNumberRollbackedEvent;
import com.smartbank.common.events.LoanMobileNumberUpdatedEvent;
import com.smartbank.loanservice.command.CreateLoanCommand;
import com.smartbank.loanservice.command.DeleteLoanCommand;
import com.smartbank.loanservice.command.UpdateLoanCommand;
import com.smartbank.loanservice.command.events.LoanCreatedEvent;
import com.smartbank.loanservice.command.events.LoanDeletedEvent;
import com.smartbank.loanservice.command.events.LoanUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

//@Aggregate(snapshotTriggerDefinition = "loanSnapshotTrigger")
@Aggregate
public class LoanAggregate {
    @AggregateIdentifier
    private Long loanNumber;
    private String mobileNumber;
    private String loanType;
    private int totalLoan;
    private int amountPaid;
    private int outstandingAmount;
    private boolean isActive;
    private String errorMessage;

    protected LoanAggregate() {}

    @CommandHandler
    public LoanAggregate(CreateLoanCommand createLoanCommand) {
        LoanCreatedEvent loanCreatedEvent = new LoanCreatedEvent();
        BeanUtils.copyProperties(createLoanCommand, loanCreatedEvent);
        LoanDataChangedEvent loanDataChangedEvent = new LoanDataChangedEvent();
        BeanUtils.copyProperties(createLoanCommand, loanDataChangedEvent);
        AggregateLifecycle.apply(loanCreatedEvent);
        AggregateLifecycle.apply(loanDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(LoanCreatedEvent loanCreatedEvent) {
        this.loanNumber = loanCreatedEvent.getLoanNumber();
        this.mobileNumber = loanCreatedEvent.getMobileNumber();
        this.loanType = loanCreatedEvent.getLoanType();
        this.totalLoan = loanCreatedEvent.getTotalLoan();
        this.amountPaid = loanCreatedEvent.getAmountPaid();
        this.outstandingAmount = loanCreatedEvent.getOutstandingAmount();
        this.isActive = loanCreatedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateLoanCommand updateLoanCommand) {
        LoanUpdatedEvent loanUpdatedEvent = new LoanUpdatedEvent();
        BeanUtils.copyProperties(updateLoanCommand, loanUpdatedEvent);
        LoanDataChangedEvent loanDataChangedEvent = new LoanDataChangedEvent();
        BeanUtils.copyProperties(updateLoanCommand, loanDataChangedEvent);
        AggregateLifecycle.apply(loanUpdatedEvent);
        AggregateLifecycle.apply(loanDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(LoanUpdatedEvent loanUpdatedEvent) {
        this.loanType = loanUpdatedEvent.getLoanType();
        this.totalLoan = loanUpdatedEvent.getTotalLoan();
        this.amountPaid = loanUpdatedEvent.getAmountPaid();
        this.outstandingAmount = loanUpdatedEvent.getOutstandingAmount();
    }

    @CommandHandler
    public void handle(DeleteLoanCommand deleteLoanCommand) {
        LoanDeletedEvent loanDeletedEvent = new LoanDeletedEvent();
        BeanUtils.copyProperties(deleteLoanCommand, loanDeletedEvent);
        AggregateLifecycle.apply(loanDeletedEvent);
    }

    @EventSourcingHandler
    public void on(LoanDeletedEvent loanDeletedEvent) {
        this.isActive = loanDeletedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateLoanMobileNumberCommand updateLoanMobileNumberCommand) {
        LoanMobileNumberUpdatedEvent loanMobileNumberUpdatedEvent = new LoanMobileNumberUpdatedEvent();
        BeanUtils.copyProperties(updateLoanMobileNumberCommand, loanMobileNumberUpdatedEvent);
        AggregateLifecycle.apply(loanMobileNumberUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(LoanMobileNumberUpdatedEvent loanMobileNumberUpdatedEvent) {
        this.mobileNumber = loanMobileNumberUpdatedEvent.getNewMobileNumber();
    }

    @CommandHandler
    public void handle(RollbackLoanMobileNumberCommand rollbackLoanMobileNumberCommand) {
        LoanMobileNumberRollbackedEvent loanMobileNumberRollbackedEvent = new LoanMobileNumberRollbackedEvent();
        BeanUtils.copyProperties(rollbackLoanMobileNumberCommand, loanMobileNumberRollbackedEvent);
        AggregateLifecycle.apply(loanMobileNumberRollbackedEvent);
    }

    @EventSourcingHandler
    public void on(LoanMobileNumberRollbackedEvent loanMobileNumberRollbackedEvent) {
        this.mobileNumber = loanMobileNumberRollbackedEvent.getMobileNumber();
        this.errorMessage = loanMobileNumberRollbackedEvent.getErrorMessage();
    }
}
