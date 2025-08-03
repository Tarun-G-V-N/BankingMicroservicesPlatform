package com.smartbank.cardservice.command.interceptor;

import com.smartbank.cardservice.command.CreateCardCommand;
import com.smartbank.cardservice.command.DeleteCardCommand;
import com.smartbank.cardservice.command.UpdateCardCommand;
import com.smartbank.cardservice.constants.CardConstants;
import com.smartbank.cardservice.entities.Card;
import com.smartbank.cardservice.exceptions.CardAlreadyExistsException;
import com.smartbank.cardservice.exceptions.ResourceNotFoundException;
import com.smartbank.cardservice.repositories.CardRepository;
import com.smartbank.common.commands.UpdateCardMobileNumberCommand;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CardCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    private final CardRepository cardRepository;
    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if(CreateCardCommand.class.equals(command.getPayloadType())) {
                CreateCardCommand createCardCommand = (CreateCardCommand) command.getPayload();
                Optional<Card> optionalCard = cardRepository.findByMobileNumberAndIsActive(createCardCommand.getMobileNumber(), CardConstants.ACTIVE_SW);
                if (optionalCard.isPresent()) throw new CardAlreadyExistsException("Card already exists with the provided mobile number"+createCardCommand.getMobileNumber());
            }
            else if(UpdateCardCommand.class.equals(command.getPayloadType())) {
                UpdateCardCommand updateCardCommand = (UpdateCardCommand) command.getPayload();
                Card card = cardRepository.findByMobileNumberAndIsActive(updateCardCommand.getMobileNumber(), CardConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", updateCardCommand.getMobileNumber()));
            }
            else if (DeleteCardCommand.class.equals(command.getPayloadType())) {
                DeleteCardCommand deleteCardCommand = (DeleteCardCommand) command.getPayload();
                Card card = cardRepository.findByCardNumberAndIsActive(deleteCardCommand.getCardNumber(), CardConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Card", "cardId", deleteCardCommand.getCardNumber().toString()));
            } else if (UpdateCardMobileNumberCommand.class.equals(command.getPayloadType())) {
                UpdateCardMobileNumberCommand updateCardMobileNumberCommand = (UpdateCardMobileNumberCommand) command.getPayload();
                Card card = cardRepository.findByMobileNumberAndIsActive(updateCardMobileNumberCommand.getMobileNumber(), CardConstants.ACTIVE_SW)
                        .orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", updateCardMobileNumberCommand.getMobileNumber()));
            }
            return command;
        };
    }
}
