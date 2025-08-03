package com.smartbank.cardservice.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class DeleteCardCommand {
    @TargetAggregateIdentifier
    private final Long cardNumber;
    private final boolean isActive;

    @JsonCreator
    public DeleteCardCommand(@JsonProperty("cardNumber") Long cardNumber, @JsonProperty("active") boolean isActive) {
        this.cardNumber = cardNumber;
        this.isActive = isActive;
    }
}
