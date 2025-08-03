package com.smartbank.cardservice.services.Impl;

import com.smartbank.cardservice.command.event.CardUpdatedEvent;
import com.smartbank.cardservice.constants.CardConstants;
import com.smartbank.cardservice.dtos.CardDTO;
import com.smartbank.cardservice.dtos.CardsMessageDTO;
import com.smartbank.cardservice.entities.Card;
import com.smartbank.cardservice.exceptions.CardAlreadyExistsException;
import com.smartbank.cardservice.exceptions.ResourceNotFoundException;
import com.smartbank.cardservice.mappers.CardMapper;
import com.smartbank.cardservice.repositories.CardRepository;
import com.smartbank.cardservice.services.ICardService;
import com.smartbank.common.events.CardDataChangedEvent;
import lombok.AllArgsConstructor;
import org.axonframework.eventhandling.gateway.EventGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardServiceImpl implements ICardService {
    public static Logger logger = LoggerFactory.getLogger(CardServiceImpl.class);
    private CardRepository cardRepository;
    private EventGateway eventGateway;
    private StreamBridge streamBridge;
    @Override
    public void createCard(Card card) {
        Optional<Card> optionalCard = cardRepository.findByMobileNumberAndIsActive(card.getMobileNumber(), CardConstants.ACTIVE_SW);
        if (optionalCard.isPresent()) throw new CardAlreadyExistsException("Card already exists with the provided mobile number"+card.getMobileNumber());
        Card savedCard = cardRepository.save(card);
        sendCommunication(savedCard);
    }

    @Override
    public CardDTO fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumberAndIsActive(mobileNumber, CardConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardMapper.mapToCardsDto(card, new CardDTO());
    }

    @Override
    public boolean updateCard(CardUpdatedEvent cardUpdatedEvent) {
        Card card = cardRepository.findByMobileNumberAndIsActive(cardUpdatedEvent.getMobileNumber(), CardConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", cardUpdatedEvent.getMobileNumber()));
        cardRepository.save(CardMapper.mapEventToCard(cardUpdatedEvent, card));
        return true;
    }

    @Override
    public boolean deleteCard(Long cardNumber) {
        Card card = cardRepository.findByCardNumberAndIsActive(cardNumber, CardConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", cardNumber.toString()));
        card.setActive(CardConstants.IN_ACTIVE_SW);
        cardRepository.save(card);
        CardDataChangedEvent cardDataChangedEvent = new CardDataChangedEvent();
        cardDataChangedEvent.setMobileNumber(card.getMobileNumber());
        cardDataChangedEvent.setCardNumber(0L);
        eventGateway.publish(cardDataChangedEvent);
        return true;
    }

    @Override
    public boolean updateMobileNumber(String mobileNumber, String newMobileNumber) {
        Card card = cardRepository.findByMobileNumberAndIsActive(mobileNumber, CardConstants.ACTIVE_SW)
                .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        card.setMobileNumber(newMobileNumber);
        cardRepository.save(card);
        return true;
    }

    private void sendCommunication(Card card) {
        var cardsMessageDTO = new CardsMessageDTO(card.getMobileNumber(), card.getCardNumber(), card.getCardType());
        logger.debug("Sending Communication request for the details: {}", cardsMessageDTO);
        var result = streamBridge.send("sendCommunication-out-0", cardsMessageDTO);
        logger.debug("is communication request successfully triggered ? : {}", result);
    }

    @Override
    public boolean updateCommunicationStatus(Long cardNumber) {
        boolean isUpdated = false;
        if(cardNumber != null) {
            Card card = cardRepository.findByCardNumberAndIsActive(cardNumber, CardConstants.ACTIVE_SW)
                    .orElseThrow(() -> new ResourceNotFoundException("Account", "accountNumber", cardNumber.toString()));
            card.setCommunicationSent(true);
            cardRepository.save(card);
            isUpdated = true;
        }
        return isUpdated;
    }
}
