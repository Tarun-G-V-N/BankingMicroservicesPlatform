package com.smartbank.cardservice.query.projection;

import com.smartbank.cardservice.command.event.CardCreatedEvent;
import com.smartbank.cardservice.command.event.CardDeletedEvent;
import com.smartbank.cardservice.command.event.CardUpdatedEvent;
import com.smartbank.cardservice.entities.Card;
import com.smartbank.cardservice.services.ICardService;
import com.smartbank.common.events.CardMobileNumberRollbackedEvent;
import com.smartbank.common.events.CardMobileNumberUpdatedEvent;
import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ProcessingGroup("card-group")
public class CardProjection {
    private final ICardService cardService;

    @EventHandler
    public void on(CardCreatedEvent cardCreatedEvent) {
        Card card = new Card();
        BeanUtils.copyProperties(cardCreatedEvent, card);
        cardService.createCard(card);
    }

    @EventHandler
    public void on(CardUpdatedEvent cardUpdatedEvent) {
        cardService.updateCard(cardUpdatedEvent);
    }

    @EventHandler
    public void on(CardDeletedEvent cardDeletedEvent) {
        cardService.deleteCard(cardDeletedEvent.getCardNumber());
    }

    @EventHandler
    public void on(CardMobileNumberUpdatedEvent cardMobileNumberUpdatedEvent) {
        cardService.updateMobileNumber(cardMobileNumberUpdatedEvent.getMobileNumber(), cardMobileNumberUpdatedEvent.getNewMobileNumber());
    }

    @EventHandler
    public void handle(CardMobileNumberRollbackedEvent cardMobileNumberRollbackedEvent) {
        cardService.updateMobileNumber(cardMobileNumberRollbackedEvent.getNewMobileNumber(), cardMobileNumberRollbackedEvent.getMobileNumber());
    }
}
