package com.smartbank.cardservice.mappers;

import com.smartbank.cardservice.command.event.CardUpdatedEvent;
import com.smartbank.cardservice.dtos.CardDTO;
import com.smartbank.cardservice.entities.Card;

public class CardMapper {
    public static CardDTO mapToCardsDto(Card cards, CardDTO cardDto) {
        cardDto.setCardNumber(cards.getCardNumber());
        cardDto.setCardType(cards.getCardType());
        cardDto.setMobileNumber(cards.getMobileNumber());
        cardDto.setTotalLimit(cards.getTotalLimit());
        cardDto.setAvailableAmount(cards.getAvailableAmount());
        cardDto.setAmountUsed(cards.getAmountUsed());
        cardDto.setActive(cards.isActive());
        return cardDto;
    }

    public static Card mapToCards(CardDTO cardDto, Card card) {
        card.setCardNumber(cardDto.getCardNumber());
        card.setCardType(cardDto.getCardType());
        card.setMobileNumber(cardDto.getMobileNumber());
        card.setTotalLimit(cardDto.getTotalLimit());
        card.setAvailableAmount(cardDto.getAvailableAmount());
        card.setAmountUsed(cardDto.getAmountUsed());
        return card;
    }

    public static Card mapEventToCard(CardUpdatedEvent cardUpdatedEvent, Card card) {
        card.setAmountUsed(cardUpdatedEvent.getAmountUsed());
        card.setAvailableAmount(cardUpdatedEvent.getAvailableAmount());
        card.setTotalLimit(cardUpdatedEvent.getTotalLimit());
        card.setCardType(cardUpdatedEvent.getCardType());
        return card;
    }
}
