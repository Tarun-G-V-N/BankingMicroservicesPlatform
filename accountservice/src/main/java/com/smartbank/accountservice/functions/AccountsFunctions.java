package com.smartbank.accountservice.functions;

import com.smartbank.accountservice.services.IAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class AccountsFunctions {

    public static Logger logger = LoggerFactory.getLogger(AccountsFunctions.class);

    @Bean
    public Consumer<Long> updateCommunication(IAccountService accountService) {
        return accountNumber -> {
            logger.debug("Updating Communication status for the account number : {} ", accountNumber.toString());
            accountService.updateCommunicationStatus(accountNumber);
        };
    }
}
