package com.smartbank.messageservice.functions;

import com.smartbank.messageservice.dtos.AccountsMessageDTO;
import com.smartbank.messageservice.dtos.CardsMessageDTO;
import com.smartbank.messageservice.dtos.LoansMessageDTO;
import com.smartbank.messageservice.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class MessageFunctions {

    public static final Logger logger = LoggerFactory.getLogger(MessageFunctions.class);
    @Autowired
    private SmsService smsService;

    @Bean
    public Function<AccountsMessageDTO, AccountsMessageDTO> emailForAccount() {
        return accountsMessageDTO -> {
            logger.debug("sending email with details:"+ accountsMessageDTO.toString());
            return accountsMessageDTO;
        };
    }

    @Bean
    public Function<AccountsMessageDTO, Long> smsForAccount() {
        return accountsMessageDTO -> {
            logger.debug("sending sms with details:"+ accountsMessageDTO.toString());
            smsService.send(accountsMessageDTO.mobileNumber(), "An account has been created with account number: " + accountsMessageDTO.accountNumber());
            return accountsMessageDTO.accountNumber();
        };
    }

    @Bean
    public Function<CardsMessageDTO, CardsMessageDTO> emailForCard() {
        return cardsMessageDTO -> {
            logger.debug("sending email with details:"+ cardsMessageDTO.toString());
            return cardsMessageDTO;
        };
    }

    @Bean
    public Function<CardsMessageDTO, Long> smsForCard() {
        return cardsMessageDTO -> {
            logger.debug("sending sms with details:"+ cardsMessageDTO.toString());
            smsService.send(cardsMessageDTO.mobileNumber(), "A "+cardsMessageDTO.cardType()+" has been created with card number: " + cardsMessageDTO.cardNumber());
            return cardsMessageDTO.cardNumber();
        };
    }

    @Bean
    public Function<LoansMessageDTO, LoansMessageDTO> emailForLoan() {
        return loansMessageDTO -> {
            logger.debug("sending email with details:"+ loansMessageDTO.toString());
            return loansMessageDTO;
        };
    }

    @Bean
    public Function<LoansMessageDTO, Long> smsForLoan() {
        return loansMessageDTO -> {
            logger.debug("sending sms with details:"+ loansMessageDTO.toString());
            smsService.send(loansMessageDTO.mobileNumber(), "A "+loansMessageDTO.loanType()+" has been created with loan number: " + loansMessageDTO.loanNumber());
            return loansMessageDTO.loanNumber();
        };
    }
}
