package com.smartbank.cardservice.query.handler;

import com.smartbank.cardservice.dtos.CardDTO;
import com.smartbank.cardservice.query.FindCardQuery;
import com.smartbank.cardservice.services.ICardService;
import lombok.RequiredArgsConstructor;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CardQueryHandler {
    private final ICardService cardService;

    @QueryHandler
    public CardDTO findCard(FindCardQuery findCardQuery) {
        return cardService.fetchCard(findCardQuery.getMobileNumber());
    }
}
