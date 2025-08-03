package com.smartbank.accountservice.services.Impl;

import com.smartbank.accountservice.command.event.AccountUpdatedEvent;
import com.smartbank.accountservice.constants.AccountConstants;
import com.smartbank.accountservice.dtos.AccountsDTO;
import com.smartbank.accountservice.dtos.AccountsMessageDTO;
import com.smartbank.accountservice.entities.Account;
import com.smartbank.accountservice.exceptions.AccountAlreadyExistsException;
import com.smartbank.accountservice.exceptions.ResourceNotFoundException;
import com.smartbank.accountservice.mappers.AccountsMapper;
import com.smartbank.accountservice.repositories.AccountRepository;
import com.smartbank.accountservice.services.IAccountService;
import com.smartbank.common.events.AccountDataChangedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service @AllArgsConstructor
public class AccountServiceImpl implements IAccountService {
    private AccountRepository accountRepository;
    private StreamBridge streamBridge;
    private EventGateway eventGateway;

    public static Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Override
    public void createAccount(Account account) {
        Optional<Account> optionalAccount = accountRepository.findByMobileNumberAndIsActive(account.getMobileNumber(), AccountConstants.ACTIVE_SW);
        if(optionalAccount.isPresent())
            throw new AccountAlreadyExistsException("Account already exists with mobile number: " + account.getMobileNumber());
        Account savedAccount = accountRepository.save(account);
        sendCommunication(savedAccount);
    }

    public AccountsDTO getAccount(String mobileNumber) {
        Account account = accountRepository.findByMobileNumberAndIsActive(mobileNumber, AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", mobileNumber));
        return AccountsMapper.mapToAccountsDTO(new AccountsDTO(), account);
    }

    public boolean updateAccount(AccountUpdatedEvent accountUpdatedEvent) {
        Account account = accountRepository.findByMobileNumberAndIsActive(accountUpdatedEvent.getMobileNumber(), AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", accountUpdatedEvent.getMobileNumber()));
        Account accountTobeSaved = AccountsMapper.mapEventToAccount(account, accountUpdatedEvent);
        accountRepository.save(accountTobeSaved);
        return true;
    }

    public boolean deleteAccount(Long accountNumber) {
        Account account = accountRepository.findByAccountNumberAndIsActive(accountNumber, AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));
        account.setActive(AccountConstants.IN_ACTIVE_SW);
        accountRepository.save(account);
        AccountDataChangedEvent accountDataChangedEvent = new AccountDataChangedEvent();
        accountDataChangedEvent.setAccountNumber(0L);
        accountDataChangedEvent.setMobileNumber(account.getMobileNumber());
        eventGateway.publish(accountDataChangedEvent);
        return true;
    }

    @Override
    public boolean updateCommunicationStatus(Long accountNumber) {
        boolean isUpdated = false;
        if(accountNumber != null) {
            Account account = accountRepository.findByAccountNumberAndIsActive(accountNumber, AccountConstants.ACTIVE_SW)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", accountNumber.toString()));
            account.setCommunicationSent(true);
            accountRepository.save(account);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    public boolean updateMobileNumber(String mobileNumber, String newMobileNumber) {
        Account account = accountRepository.findByMobileNumberAndIsActive(mobileNumber, AccountConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Account", "mobileNumber", mobileNumber));
        account.setMobileNumber(newMobileNumber);
        accountRepository.save(account);
        return true;
    }

    private void sendCommunication(Account account) {
        var accountsMessageDTO = new AccountsMessageDTO(account.getAccountNumber(), account.getAccountType(), account.getMobileNumber());
        logger.debug("Sending Communication request for the details: {}", accountsMessageDTO);
        var result = streamBridge.send("sendCommunication-out-0", accountsMessageDTO);
        logger.debug("is communication request successfully triggered ? : {}", result);
    }
}
