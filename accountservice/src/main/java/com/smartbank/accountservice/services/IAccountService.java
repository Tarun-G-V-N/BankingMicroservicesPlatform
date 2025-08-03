package com.smartbank.accountservice.services;

import com.smartbank.accountservice.command.event.AccountUpdatedEvent;
import com.smartbank.accountservice.dtos.AccountsDTO;
import com.smartbank.accountservice.entities.Account;

public interface IAccountService {
    void createAccount(Account account);
    AccountsDTO getAccount(String mobileNumber);
    boolean updateAccount(AccountUpdatedEvent accountUpdatedEvent);
    boolean deleteAccount(Long accountNumber);
    boolean updateCommunicationStatus(Long accountNumber);
    boolean updateMobileNumber(String mobileNumber, String newMobileNumber);
}
