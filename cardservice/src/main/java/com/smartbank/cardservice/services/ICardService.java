package com.smartbank.cardservice.services;

import com.smartbank.cardservice.command.event.CardUpdatedEvent;
import com.smartbank.cardservice.dtos.CardDTO;
import com.smartbank.cardservice.entities.Card;

public interface ICardService {
    void createCard(Card card);
    CardDTO fetchCard(String mobileNumber);
    boolean updateCard(CardUpdatedEvent cardUpdatedEvent);
    boolean deleteCard(Long cardNumber);
    boolean updateMobileNumber(String mobileNumber, String newMobileNumber);
    boolean updateCommunicationStatus(Long cardNumber);
}
