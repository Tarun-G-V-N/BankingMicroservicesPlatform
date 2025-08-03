package com.smartbank.accountservice.command.aggregate;

import com.smartbank.accountservice.command.CreateAccountCommand;
import com.smartbank.accountservice.command.DeleteAccountCommand;
import com.smartbank.accountservice.command.UpdateAccountCommand;
import com.smartbank.accountservice.command.event.AccountCreatedEvent;
import com.smartbank.accountservice.command.event.AccountDeletedEvent;
import com.smartbank.accountservice.command.event.AccountUpdatedEvent;
import com.smartbank.common.commands.RollbackAccountMobileNumberCommand;
import com.smartbank.common.commands.UpdateAccountMobileNumberCommand;
import com.smartbank.common.events.AccountDataChangedEvent;
import com.smartbank.common.events.AccountMobileNumberRollbackedEvent;
import com.smartbank.common.events.AccountMobileNumberUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

//@Aggregate(snapshotTriggerDefinition = "accountSnapshotTrigger")
@Aggregate
public class AccountAggregate {
    @AggregateIdentifier
    private Long accountNumber;
    private String mobileNumber;
    private String accountType;
    private String branchAddress;
    private boolean isActive;
    private String errorMessage;

    protected AccountAggregate() {}

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent();
        BeanUtils.copyProperties(createAccountCommand, accountCreatedEvent);
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        BeanUtils.copyProperties(createAccountCommand, accountDataChangedEvent);
        AggregateLifecycle.apply(accountCreatedEvent);
        AggregateLifecycle.apply(accountDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        this.accountNumber = accountCreatedEvent.getAccountNumber();
        this.mobileNumber = accountCreatedEvent.getMobileNumber();
        this.accountType = accountCreatedEvent.getAccountType();
        this.branchAddress = accountCreatedEvent.getBranchAddress();
        this.isActive = accountCreatedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateAccountCommand updateAccountCommand) {
        AccountUpdatedEvent accountUpdatedEvent = new AccountUpdatedEvent();
        BeanUtils.copyProperties(updateAccountCommand, accountUpdatedEvent);
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        BeanUtils.copyProperties(updateAccountCommand, accountDataChangedEvent);
        AggregateLifecycle.apply(accountUpdatedEvent);
        AggregateLifecycle.apply(accountDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(AccountUpdatedEvent accountUpdatedEvent) {
        this.accountType = accountUpdatedEvent.getAccountType();
        this.branchAddress = accountUpdatedEvent.getBranchAddress();
    }

    @CommandHandler
    public void handle(DeleteAccountCommand deleteAccountCommand) {
        AccountDeletedEvent accountDeletedEvent = new AccountDeletedEvent();
        BeanUtils.copyProperties(deleteAccountCommand, accountDeletedEvent);
        AggregateLifecycle.apply(accountDeletedEvent);
    }

    @EventSourcingHandler
    public void on(AccountDeletedEvent accountDeletedEvent) {
        this.isActive = accountDeletedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateAccountMobileNumberCommand updateAccountMobileNumberCommand) {
        AccountMobileNumberUpdatedEvent accountMobileNumberUpdatedEvent = new AccountMobileNumberUpdatedEvent();
        BeanUtils.copyProperties(updateAccountMobileNumberCommand, accountMobileNumberUpdatedEvent);
        AggregateLifecycle.apply(accountMobileNumberUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(AccountMobileNumberUpdatedEvent accountMobileNumberUpdatedEvent) {
        this.mobileNumber = accountMobileNumberUpdatedEvent.getNewMobileNumber();
    }

    @CommandHandler
    public void handle(RollbackAccountMobileNumberCommand rollbackAccountMobileNumberCommand) {
        AccountMobileNumberRollbackedEvent accountMobileNumberRollbackedEvent = new AccountMobileNumberRollbackedEvent();
        BeanUtils.copyProperties(rollbackAccountMobileNumberCommand, accountMobileNumberRollbackedEvent);
        AggregateLifecycle.apply(accountMobileNumberRollbackedEvent);
    }

    @EventSourcingHandler
    public void on(AccountMobileNumberRollbackedEvent accountMobileNumberRollbackedEvent) {
        this.mobileNumber = accountMobileNumberRollbackedEvent.getMobileNumber();
        this.errorMessage = accountMobileNumberRollbackedEvent.getErrorMessage();
    }
}
