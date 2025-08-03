package com.smartbank.cardservice.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class UpdateCardCommand {
    @TargetAggregateIdentifier
    private final Long cardNumber;
    private final String mobileNumber;
    private final String cardType;
    private final int totalLimit;
    private final int amountUsed;
    private final int availableAmount;
    private final boolean isActive;

    @JsonCreator
    public UpdateCardCommand(@JsonProperty("cardNumber") Long cardNumber, @JsonProperty("mobileNumber") String mobileNumber,
                             @JsonProperty("cardType") String cardType, @JsonProperty("totalLimit") int totalLimit,
                             @JsonProperty("amountUsed") int amountUsed, @JsonProperty("availableAmount") int availableAmount,
                             @JsonProperty("active") boolean isActive) {
        this.cardNumber = cardNumber;
        this.mobileNumber = mobileNumber;
        this.cardType = cardType;
        this.totalLimit = totalLimit;
        this.amountUsed = amountUsed;
        this.availableAmount = availableAmount;
        this.isActive = isActive;
    }
}
