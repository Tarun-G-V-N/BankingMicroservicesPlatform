package com.smartbank.cardservice.functions;

import com.smartbank.cardservice.services.ICardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
public class CardFunction {
    public static Logger logger = LoggerFactory.getLogger(CardFunction.class);

    @Bean
    public Consumer<Long> updateCommunication(ICardService cardService) {
        return cardNumber -> {
            logger.debug("Updating Communication status for the card number : {} ", cardNumber.toString());
            cardService.updateCommunicationStatus(cardNumber);
        };
    }
}
