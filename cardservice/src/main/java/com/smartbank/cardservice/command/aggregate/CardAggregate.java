package com.smartbank.cardservice.command.aggregate;

import com.smartbank.cardservice.command.CreateCardCommand;
import com.smartbank.cardservice.command.DeleteCardCommand;
import com.smartbank.cardservice.command.UpdateCardCommand;
import com.smartbank.cardservice.command.event.CardCreatedEvent;
import com.smartbank.cardservice.command.event.CardDeletedEvent;
import com.smartbank.cardservice.command.event.CardUpdatedEvent;
import com.smartbank.common.commands.RollbackCardMobileNumberCommand;
import com.smartbank.common.commands.UpdateCardMobileNumberCommand;
import com.smartbank.common.events.CardDataChangedEvent;
import com.smartbank.common.events.CardMobileNumberRollbackedEvent;
import com.smartbank.common.events.CardMobileNumberUpdatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

//@Aggregate(snapshotTriggerDefinition = "cardSnapshotTrigger")
@Aggregate
public class CardAggregate {
    @AggregateIdentifier
    private Long cardNumber;
    private String mobileNumber;
    private String cardType;
    private int totalLimit;
    private int amountUsed;
    private int availableAmount;
    private boolean isCardActive;
    private String errorMessage;

    protected CardAggregate() {}

    @CommandHandler
    public CardAggregate(CreateCardCommand createCardCommand) {
        CardCreatedEvent cardCreatedEvent = new CardCreatedEvent();
        BeanUtils.copyProperties(createCardCommand, cardCreatedEvent);
        CardDataChangedEvent cardDataChangedEvent = new CardDataChangedEvent();
        BeanUtils.copyProperties(createCardCommand, cardDataChangedEvent);
        AggregateLifecycle.apply(cardCreatedEvent);
        AggregateLifecycle.apply(cardDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CardCreatedEvent cardCreatedEvent) {
        this.cardNumber = cardCreatedEvent.getCardNumber();
        this.mobileNumber = cardCreatedEvent.getMobileNumber();
        this.cardType = cardCreatedEvent.getCardType();
        this.totalLimit = cardCreatedEvent.getTotalLimit();
        this.amountUsed = cardCreatedEvent.getAmountUsed();
        this.availableAmount = cardCreatedEvent.getAvailableAmount();
        this.isCardActive = cardCreatedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateCardCommand updateCardCommand) {
        CardUpdatedEvent cardUpdatedEvent = new CardUpdatedEvent();
        BeanUtils.copyProperties(updateCardCommand, cardUpdatedEvent);
        CardDataChangedEvent cardDataChangedEvent = new CardDataChangedEvent();
        BeanUtils.copyProperties(updateCardCommand, cardDataChangedEvent);
        AggregateLifecycle.apply(cardUpdatedEvent);
        AggregateLifecycle.apply(cardDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CardUpdatedEvent cardUpdatedEvent) {
        this.amountUsed = cardUpdatedEvent.getAmountUsed();
        this.availableAmount = cardUpdatedEvent.getAvailableAmount();
        this.totalLimit = cardUpdatedEvent.getTotalLimit();
        this.cardType = cardUpdatedEvent.getCardType();
    }

    @CommandHandler
    public void handle(DeleteCardCommand deleteCardCommand) {
        CardDeletedEvent cardDeletedEvent = new CardDeletedEvent();
        BeanUtils.copyProperties(deleteCardCommand, cardDeletedEvent);
        AggregateLifecycle.apply(cardDeletedEvent);
    }

    @EventSourcingHandler
    public void on(CardDeletedEvent cardDeletedEvent) {
        this.isCardActive = cardDeletedEvent.isActive();
    }

    @CommandHandler
    public void handle(UpdateCardMobileNumberCommand updateCardMobileNumberCommand) {
        CardMobileNumberUpdatedEvent cardMobileNumberUpdatedEvent = new CardMobileNumberUpdatedEvent();
        BeanUtils.copyProperties(updateCardMobileNumberCommand, cardMobileNumberUpdatedEvent);
        AggregateLifecycle.apply(cardMobileNumberUpdatedEvent);
    }

    @EventSourcingHandler
    public void on(CardMobileNumberUpdatedEvent cardMobileNumberUpdatedEvent) {
        this.mobileNumber = cardMobileNumberUpdatedEvent.getNewMobileNumber();
    }

    @CommandHandler
    public void handle(RollbackCardMobileNumberCommand rollbackCardMobileNumberCommand) {
        CardMobileNumberRollbackedEvent cardMobileNumberRollbackedEvent = new CardMobileNumberRollbackedEvent();
        BeanUtils.copyProperties(rollbackCardMobileNumberCommand, cardMobileNumberRollbackedEvent);
        AggregateLifecycle.apply(cardMobileNumberRollbackedEvent);
    }

    @EventSourcingHandler
    public void on(CardMobileNumberRollbackedEvent cardMobileNumberRollbackedEvent) {
        this.mobileNumber = cardMobileNumberRollbackedEvent.getMobileNumber();
        this.errorMessage = cardMobileNumberRollbackedEvent.getErrorMessage();
    }
}
