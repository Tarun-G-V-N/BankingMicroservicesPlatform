package com.smartbank.accountservice.mappers;

import com.smartbank.accountservice.command.event.AccountUpdatedEvent;
import com.smartbank.accountservice.dtos.AccountsDTO;
import com.smartbank.accountservice.entities.Account;

public class AccountsMapper {
    public static AccountsDTO mapToAccountsDTO(AccountsDTO accountsDTO, Account account) {
        accountsDTO.setAccountNumber(account.getAccountNumber());
        accountsDTO.setAccountType(account.getAccountType());
        accountsDTO.setBranchAddress(account.getBranchAddress());
        accountsDTO.setMobileNumber(account.getMobileNumber());
        accountsDTO.setActive(account.isActive());
        return accountsDTO;
    }
    public static Account mapToAccount(Account account, AccountsDTO accountsDTO) {
        account.setAccountType(accountsDTO.getAccountType());
        account.setBranchAddress(accountsDTO.getBranchAddress());
        return account;
    }

    public static Account mapEventToAccount(Account account, AccountUpdatedEvent accountUpdatedEvent) {
        account.setAccountType(accountUpdatedEvent.getAccountType());
        account.setBranchAddress(accountUpdatedEvent.getBranchAddress());
        return account;
    }
}
