package com.smartbank.loanservice.functions;

import com.smartbank.loanservice.services.ILoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class LoanFunction {

    public static Logger logger = LoggerFactory.getLogger(LoanFunction.class);

    @Bean
    public Consumer<Long> updateCommunication(ILoanService loanService) {
        return loanNumber -> {
            logger.debug("Updating Communication status for the loan number : {} ", loanNumber.toString());
            loanService.updateCommunicationStatus(loanNumber);
        };
    }
}
