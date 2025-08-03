package com.smartbank.accountservice.query.handler;

import com.smartbank.accountservice.dtos.AccountsDTO;
import com.smartbank.accountservice.query.FindAccountQuery;
import com.smartbank.accountservice.services.IAccountService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountQueryHandler {
    private final IAccountService accountService;

    @QueryHandler
    public AccountsDTO findAccount(FindAccountQuery findAccountQuery) {
        return accountService.getAccount(findAccountQuery.getMobileNumber());
    }
}
