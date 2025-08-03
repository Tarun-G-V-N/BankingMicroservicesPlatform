package com.smartbank.accountservice.query.projection;

import com.smartbank.accountservice.command.event.AccountCreatedEvent;
import com.smartbank.accountservice.command.event.AccountDeletedEvent;
import com.smartbank.accountservice.command.event.AccountUpdatedEvent;
import com.smartbank.accountservice.entities.Account;
import com.smartbank.accountservice.services.IAccountService;
import com.smartbank.common.events.AccountMobileNumberRollbackedEvent;
import com.smartbank.common.events.AccountMobileNumberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("account-group")
public class AccountProjection {
    private final IAccountService accountService;
    @EventHandler
    public void on(AccountCreatedEvent accountCreatedEvent) {
        Account account = new Account();
        BeanUtils.copyProperties(accountCreatedEvent, account);
        accountService.createAccount(account);
    }

    @EventHandler
    public void on(AccountUpdatedEvent accountUpdatedEvent) {
        accountService.updateAccount(accountUpdatedEvent);
    }

    @EventHandler
    public void on(AccountDeletedEvent accountDeletedEvent) {
        accountService.deleteAccount(accountDeletedEvent.getAccountNumber());
    }

    @EventHandler
    public void on(AccountMobileNumberUpdatedEvent accountMobileNumberUpdatedEvent) {
        accountService.updateMobileNumber(accountMobileNumberUpdatedEvent.getMobileNumber(), accountMobileNumberUpdatedEvent.getNewMobileNumber());
    }

    @EventHandler
    public void on(AccountMobileNumberRollbackedEvent accountMobileNumberRollbackedEvent) {
        accountService.updateMobileNumber(accountMobileNumberRollbackedEvent.getNewMobileNumber(), accountMobileNumberRollbackedEvent.getMobileNumber());
    }
}
